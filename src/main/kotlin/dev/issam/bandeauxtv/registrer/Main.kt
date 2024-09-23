package dev.issam.bandeauxtv.registrer

import com.natpryce.konfig.*
import dev.issam.bandeauxtv.registrer.channels.BfmReader

import dev.issam.bandeauxtv.registrer.properties.ImagesProperties
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main() {
    val properties = ConfigurationProperties.fromResource("defaults.properties")
    val mongodbProperties = MongodbProperties(properties)
    val imagesProperties = ImagesProperties(properties)

    val bfmReader = BfmReader(imagesProperties, mongodbProperties)
    coroutineScope {
        launch { bfmReader.watchFolderAndReadBandeaux() }
    }

}

