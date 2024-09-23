package dev.issam.bandeauxtv.registrer.image

import dev.issam.bandeauxtv.registrer.channels.BfmBandeaux
import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class CropImagePartFromScreenshot(val imagesProperties: ImagesProperties) {

    fun cropAndStoreOnFilesystem(screenshot: ScreenshotImage, imagePartCoordinates: Rectangle) {
        val bandeauImage = crop(screenshot, BfmBandeaux().bandeauPrincipalRectangle)
        ImageIO.write(
            bandeauImage,
            imagesProperties.bandeauImageFormat,
            File(screenshot.folder + screenshot.screenshotFilename + imagesProperties.bandeauFilenameSuffix)
        )
    }

    private fun crop(src: ScreenshotImage, imagePartCoordinates: Rectangle): BufferedImage {
        return src.bufferedImage.getSubimage(imagePartCoordinates.x,
            imagePartCoordinates.y,
            imagePartCoordinates.width,
            imagePartCoordinates.height)
    }

}
