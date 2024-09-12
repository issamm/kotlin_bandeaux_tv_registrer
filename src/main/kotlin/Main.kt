import com.mongodb.MongoException
import java.io.File
import javax.imageio.ImageIO
import com.natpryce.konfig.*
import model.ScreenshotDocument
import org.bson.BsonDateTime
import org.bson.BsonInt64
import org.bson.Document
import org.bson.types.ObjectId
import java.time.Instant
import java.time.LocalDateTime
import java.util.Calendar

val rootPath = "C:/Users/issam.elhachimi/Desktop/bandeaux-tv-data/"
val bfmPath = rootPath + "bfm/"
val bfmScreenShotsPath = bfmPath + "screenshots/"
val bfmBandeauxPath = bfmPath + "bandeaux/"
val screenshotFilename = "screenshot.png"
val bandeauFilename = screenshotFilename + "_bandeau.png"
val bandeauFilepath = bfmBandeauxPath + bandeauFilename

class MongodbProperties(properties: ConfigurationProperties) {
    val url = properties[Key("mongodb.url", stringType)]
    val port = properties[Key("mongodb.port", intType)]
    val databaseName = properties[Key("mongodb.databaseName", stringType)]
    val username = properties[Key("mongodb.username", stringType)]
    val password = properties[Key("mongodb.password", stringType)]
}

suspend fun main() {
    val properties = ConfigurationProperties.fromResource("defaults.properties")
    val mongoDbProperties = MongodbProperties(properties)

    println(mongoDbProperties.url)
    println(mongoDbProperties.databaseName)
    println(mongoDbProperties.username)
    println(mongoDbProperties.password)

    val bandeauImageFormat = "png"
    val screenshot = ScreenshotImage(bfmScreenShotsPath + screenshotFilename)
    val bfmConfiguration = BfmConfiguration();
    val extractor = ExtractBandeauPrincipalImage()
    val bandeauImage =
        extractor.extractBandeauPrincipalImageFromScreenshot(screenshot, bfmConfiguration.bandeauPrincipalRectangle())
    ImageIO.write(bandeauImage, bandeauImageFormat, File(bandeauFilepath))
    val bandeauPrincipalText = ReadTextFromBandeauImage(bandeauFilepath).readLine()
    println("J'ai lu $bandeauPrincipalText")

    val mongoClient = MongoDbClient().mongodbConnection(mongoDbProperties)
    val database = mongoClient.getDatabase(databaseName = mongoDbProperties.databaseName)
    try {
        // Send a ping to confirm a successful connection
        val command = Document("ping", BsonInt64(1))
        database.runCommand(command)
        println("Pinged your deployment. You successfully connected to MongoDB!!!!!!!!!!!")
        val collection = database.getCollection<ScreenshotDocument>(collectionName =  "screenshots");
        collection.insertOne(ScreenshotDocument(ObjectId(), LocalDateTime.now(), bandeauPrincipalText))
    } catch (me: MongoException) {
        System.err.println(me)
    }
}

