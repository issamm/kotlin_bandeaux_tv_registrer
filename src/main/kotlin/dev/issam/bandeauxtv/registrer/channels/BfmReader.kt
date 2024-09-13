package dev.issam.bandeauxtv.registrer.channels

import dev.issam.bandeauxtv.registrer.image.CropImagePartFromScreenshot
import dev.issam.bandeauxtv.registrer.image.ReadTextFromImage
import dev.issam.bandeauxtv.registrer.image.ScreenshotImage
import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.Files
import javax.imageio.ImageIO

class BfmReader(val imagesProperties: ImagesProperties) {

    val folderToWatch = imagesProperties.rootPath + imagesProperties.bfmSubPath + imagesProperties.screenshotsSubPath
    val bandeauxFolderpath = imagesProperties.rootPath + imagesProperties.bfmSubPath + imagesProperties.bandeauxSubPath

    
    fun watchFolderAndReadBandeaux() {
        while (true)
            if (File(folderToWatch).listFiles()?.size ?: 0 > 0)
                for (screenshotFilename in File(folderToWatch).listFiles()
                    .filter { file -> file.isFile }
                    .filter { file -> "png".equals(file.extension, true) || "jpg".equals(file.extension, false) }
                    .map { file -> file.name })
                    readTheNewScreenshots(folderToWatch, screenshotFilename)
    }

    private fun readTheNewScreenshots(folderToWatch: String, screenshotFilename: String) = runBlocking {
        delay(1000)
        println("Files !!! $folderToWatch $screenshotFilename")
        cropBandeauFromScreenshot(folderToWatch, screenshotFilename)
        val bandeauPrincipalText =
            ReadTextFromImage(bandeauxFolderpath + screenshotFilename + imagesProperties.bandeauFilenameSuffix)
                .read()
        println("J'ai lu $bandeauPrincipalText")
        moveToArchiveFolder(folderToWatch, screenshotFilename, imagesProperties.screenshotsArchiveSubPath)
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

    private fun cropBandeauFromScreenshot(folderToWatch: String, screenshotFilename: String) {
        val screenshot = ScreenshotImage(folderToWatch + screenshotFilename)
        val bandeauImage = CropImagePartFromScreenshot()
            .crop(screenshot, BfmBandeaux().bandeauPrincipalRectangle)
        ImageIO.write(
            bandeauImage,
            imagesProperties.bandeauImageFormat,
            File(bandeauxFolderpath + screenshotFilename + imagesProperties.bandeauFilenameSuffix)
        )
    }
}
