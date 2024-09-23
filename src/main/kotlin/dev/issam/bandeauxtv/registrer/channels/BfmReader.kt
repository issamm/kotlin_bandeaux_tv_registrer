package dev.issam.bandeauxtv.registrer.channels

import dev.issam.bandeauxtv.registrer.dao.ScreenshotDb
import dev.issam.bandeauxtv.registrer.image.*
import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class BfmReader(private val imagesProperties: ImagesProperties,
                private val mongodbProperties: MongodbProperties) {

    private val folderToWatch = imagesProperties.rootPath + imagesProperties.bfmSubPath + imagesProperties.screenshotsSubPath

    fun watchFolderAndReadBandeaux() {
        WatchFilesInFolderAndCallback().watchFolderAndCallback(folderToWatch, ::readTheNewScreenshot)
    }

    private fun readTheNewScreenshot(screenshotImage: ScreenshotImage) = runBlocking {
        // Attendre la fin de l'enregistrement de l'image par VLC (bufferisée, erreur rencontrée)
        delay(100)

        CropImagePartFromScreenshot(imagesProperties).cropAndStoreOnFilesystem(screenshotImage, BfmBandeaux().bandeauPrincipalRectangle)
        screenshotImage.bandeauPrincipalText =
            ReadTextFromImage(screenshotImage.screenFilepath() + imagesProperties.bandeauFilenameSuffix)
            .read()
        ScreenshotDb(mongodbProperties).storeScreenshotAndBandeau(screenshotImage)
        MoveImageIntoArchiveFolder(imagesProperties).move(screenshotImage)
    }

}
