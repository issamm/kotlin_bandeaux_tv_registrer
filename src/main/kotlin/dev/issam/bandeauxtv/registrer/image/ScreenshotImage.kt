package dev.issam.bandeauxtv.registrer.image

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ScreenshotImage() {
    lateinit var bandeauPrincipalText: String
    lateinit var bufferedImage: BufferedImage
    lateinit var folder: String
    lateinit var screenshotFilename: String

    constructor (folder: String, screenshotFilename: String) : this() {
        bufferedImage = loadScreenshotImage(folder + screenshotFilename)
        this.folder = folder
        this.screenshotFilename = screenshotFilename
    }

    private fun loadScreenshotImage(screenshotImagePath: String): BufferedImage {
        val imageFile = File(screenshotImagePath)
        return ImageIO.read(imageFile)
    }

    fun screenFilepath(): String = folder + screenshotFilename

    /*
    fun setBandeauPrincipalText(bandeauPrincipalText: String): Unit {
        this.bandeauPrincipalText = bandeauPrincipalText
    }*/
}
