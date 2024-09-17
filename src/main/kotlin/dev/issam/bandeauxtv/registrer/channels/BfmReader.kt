package dev.issam.bandeauxtv.registrer.channels

import dev.issam.bandeauxtv.registrer.dao.ScreenshotRepository
import dev.issam.bandeauxtv.registrer.image.CropImagePartFromScreenshot
import dev.issam.bandeauxtv.registrer.image.ReadTextFromImage
import dev.issam.bandeauxtv.registrer.image.ScreenshotImage
import dev.issam.bandeauxtv.registrer.image.WatchFilesInFolderAndCallback
import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.Files
import javax.imageio.ImageIO

class BfmReader(val imagesProperties: ImagesProperties, val mongodbProperties: MongodbProperties) {

    val folderToWatch = imagesProperties.rootPath + imagesProperties.bfmSubPath + imagesProperties.screenshotsSubPath
    val bandeauxFolderpath = imagesProperties.rootPath + imagesProperties.bfmSubPath + imagesProperties.bandeauxSubPath

    fun watchFolderAndReadBandeaux() {
        WatchFilesInFolderAndCallback().watchFolderAndCallback(folderToWatch, ::readTheNewScreenshot)
    }

    private fun readTheNewScreenshot(folderToWatch: String, screenshotFilename: String) = runBlocking {
        delay(1000)
        println("New screenshot to read = $folderToWatch$screenshotFilename")
        val screenshot = ScreenshotImage(folderToWatch, screenshotFilename)
        cropBandeauFromScreenshot(screenshot)
        val bandeauPrincipalText = ReadTextFromImage(bandeauxFolderpath + screenshotFilename + imagesProperties.bandeauFilenameSuffix)
                .read()
        println("J'ai lu $bandeauPrincipalText")
        moveToArchiveFolder(folderToWatch, screenshotFilename, imagesProperties.screenshotsArchiveSubPath)
        ScreenshotRepository(mongodbProperties).storeScreenshotInDb(bandeauPrincipalText)
    }

    private fun moveToArchiveFolder(
        folderToWatch: String,
        screenshotFilename: String,
        screenshotsArchiveSubPath: String
    ) {
        val fileToMove = File(folderToWatch + screenshotFilename)
        val destinationFile = File(imagesProperties.rootPath + imagesProperties.bfmSubPath + screenshotsArchiveSubPath + screenshotFilename)
        if (destinationFile.exists()) {
            fileToMove.delete()
        } else {
            Files.move( fileToMove.toPath(), destinationFile.toPath())
        }
    }

    private fun cropBandeauFromScreenshot(screenshot: ScreenshotImage) {
        val bandeauImage = CropImagePartFromScreenshot()
            .crop(screenshot, BfmBandeaux().bandeauPrincipalRectangle)
        ImageIO.write(
            bandeauImage,
            imagesProperties.bandeauImageFormat,
            File(bandeauxFolderpath + screenshot.screenshotFilename + imagesProperties.bandeauFilenameSuffix)
        )
    }
}
