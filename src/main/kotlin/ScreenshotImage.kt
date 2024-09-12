import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ScreenshotImage() {
    lateinit var bufferedImage: BufferedImage

    constructor (bufferedImagePath: String) : this() {
        bufferedImage = loadScreenshotImage(bufferedImagePath)
    }

    private fun loadScreenshotImage(path: String): BufferedImage {
        val imageFile = File(path)
        return ImageIO.read(imageFile)
    }

}
