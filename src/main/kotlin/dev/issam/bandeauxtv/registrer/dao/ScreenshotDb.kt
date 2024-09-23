package dev.issam.bandeauxtv.registrer.dao

import com.mongodb.MongoException
import dev.issam.bandeauxtv.registrer.ChannelsEnum
import dev.issam.bandeauxtv.registrer.image.ScreenshotImage
import dev.issam.bandeauxtv.registrer.model.ScreenshotDocument
import dev.issam.bandeauxtv.registrer.mongo.CollectionsEnum
import dev.issam.bandeauxtv.registrer.mongo.MongoDbClient
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties

import org.bson.types.ObjectId
import java.time.LocalDateTime

class ScreenshotDb(private val mongodbProperties: MongodbProperties) {

    suspend fun storeScreenshotAndBandeau(screenshotImage: ScreenshotImage): Unit {
        try {
            val collection = MongoDbClient(mongodbProperties).db()
                .getCollection<ScreenshotDocument>(collectionName = CollectionsEnum.SCREENSHOT.collectionName);
            collection.insertOne(screenshotDocument(screenshotImage.bandeauPrincipalText))
        } catch (me: MongoException) {
            System.err.println(me)
        }
    }

    private fun screenshotDocument(bandeauPrincipalText: String) = ScreenshotDocument(
        ObjectId(),
        ChannelsEnum.BFM.channelName,
        LocalDateTime.now(),
        bandeauPrincipalText
    )
}
