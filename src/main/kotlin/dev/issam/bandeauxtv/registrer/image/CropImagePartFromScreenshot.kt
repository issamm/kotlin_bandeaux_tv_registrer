package dev.issam.bandeauxtv.registrer.image

import java.awt.Rectangle
import java.awt.image.BufferedImage

class CropImagePartFromScreenshot {

    fun crop(src: ScreenshotImage, imagePartCoordinates: Rectangle): BufferedImage {
        return src.bufferedImage.getSubimage(imagePartCoordinates.x,
            imagePartCoordinates.y,
            imagePartCoordinates.width,
            imagePartCoordinates.height)
    }

}
