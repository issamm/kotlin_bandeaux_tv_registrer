package dev.issam.bandeauxtv.registrer.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class ScreenshotDocument(
    @BsonId
    val id: ObjectId,
    val channelName: String,
    val screenshotDatetime: LocalDateTime,
    val bandeauPrincipalText: String
)
