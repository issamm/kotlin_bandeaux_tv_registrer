package dev.issam.bandeauxtv.registrer.image

import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import java.io.File
import java.nio.file.Files

class MoveImageIntoArchiveFolder(private val imagesProperties: ImagesProperties) {

    fun move(screenshot: ScreenshotImage) {
        val fileToMove = File(screenshot.screenFilepath())
        val destinationFile =
            File(imagesProperties.rootPath + imagesProperties.bfmSubPath + imagesProperties.screenshotsArchiveSubPath + screenshot.screenshotFilename)
        if (destinationFile.exists()) {
            fileToMove.delete()
        } else {
            Files.move(fileToMove.toPath(), destinationFile.toPath())
        }
    }
}
