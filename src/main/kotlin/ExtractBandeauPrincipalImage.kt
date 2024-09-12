import java.awt.Rectangle
import java.awt.image.BufferedImage

class ExtractBandeauPrincipalImage {

    fun extractBandeauPrincipalImageFromScreenshot(src: ScreenshotImage, bandeauRectangle: Rectangle): BufferedImage {
        return src.bufferedImage.getSubimage(bandeauRectangle.x, bandeauRectangle.y, bandeauRectangle.width, bandeauRectangle.height)
    }

}
