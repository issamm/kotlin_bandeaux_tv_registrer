package dev.issam.bandeauxtv.registrer

import com.mongodb.MongoException
import com.natpryce.konfig.*
import dev.issam.bandeauxtv.registrer.channels.BfmReader
import dev.issam.bandeauxtv.registrer.model.ScreenshotDocument
import dev.issam.bandeauxtv.registrer.mongo.MongoDbClient
import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.bson.BsonInt64
import org.bson.Document


suspend fun main() {
    val properties = ConfigurationProperties.fromResource("defaults.properties")
    val mongoDbProperties = MongodbProperties(properties)
    val imagesProperties = ImagesProperties(properties)

    val bfmReader = BfmReader(imagesProperties)
    coroutineScope {
        launch { bfmReader.watchFolderAndReadBandeaux() }
    }


    val mongoClient = MongoDbClient().mongodbConnection(mongoDbProperties)
    val database = mongoClient.getDatabase(databaseName = mongoDbProperties.databaseName)
    try {
        // Send a ping to confirm a successful connection
        val command = Document("ping", BsonInt64(1))
        database.runCommand(command)
        println("Pinged your deployment. You successfully connected to MongoDB!!!!!!!!!!!")
        val collection = database.getCollection<ScreenshotDocument>(collectionName =  "screenshots");
        // collection.insertOne(ScreenshotDocument(ObjectId(), LocalDateTime.now(), bandeauPrincipalText))
    } catch (me: MongoException) {
        System.err.println(me)
    }
}

