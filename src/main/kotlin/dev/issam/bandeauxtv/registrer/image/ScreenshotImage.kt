package dev.issam.bandeauxtv.registrer.image

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ScreenshotImage() {
    lateinit var bufferedImage: BufferedImage

    constructor (bufferedImagePath: String) : this() {
        bufferedImage = loadScreenshotImage(bufferedImagePath)
    }

    private fun loadScreenshotImage(screenshotImagePath: String): BufferedImage {
        val imageFile = File(screenshotImagePath)
        return ImageIO.read(imageFile)
    }

}
