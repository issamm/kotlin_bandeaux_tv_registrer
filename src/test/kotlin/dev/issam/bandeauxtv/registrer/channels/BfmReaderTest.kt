package dev.issam.bandeauxtv.registrer.channels

import com.natpryce.konfig.ConfigurationProperties
import dev.issam.bandeauxtv.registrer.image.ScreenshotImage
import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties
import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Files
import kotlin.test.Test

class BfmReaderTest {

    @Test
    fun givenSimpleScreenshot_() {
        val properties = ConfigurationProperties.fromResource("defaults.properties")
        val mongodbProperties = MongodbProperties(properties)
        val imagesProperties = ImagesProperties(properties)
        
        val fileToCopy: File = File("C:/Users/issam.elhachimi/Desktop/bandeaux-tv-data/bfm/screenshot.png")
        val destinationFile: File = File("C:/Users/issam.elhachimi/Desktop/bandeaux-tv-data/bfm/screenshots/screenshot.png")
        Files.copy( fileToCopy.toPath(), destinationFile.toPath())
        
        val reader: BfmReader = BfmReader(imagesProperties, mongodbProperties)
        val image: ScreenshotImage = ScreenshotImage("C:/Users/issam.elhachimi/Desktop/bandeaux-tv-data/bfm/screenshots","screenshot.png")

        //reader.readBandeauPrincipal(image)
        
    }
}
