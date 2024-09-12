package model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class ScreenshotDocument(
    @BsonId
    val id: ObjectId,
    val screenshotDatetime: LocalDateTime,
    val bandeauPrincipalText: String
)
