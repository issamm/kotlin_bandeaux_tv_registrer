package dev.issam.bandeauxtv.registrer.properties

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType

class ImagesProperties(properties: ConfigurationProperties) {
    val rootPath = properties[Key("images.rootPath", stringType)]
    val bfmSubPath = properties[Key("images.bfmSubPath", stringType)]
    val bandeauxSubPath = properties[Key("images.bandeauxSubPath", stringType)]
    val screenshotsSubPath = properties[Key("images.screenshotsSubPath", stringType)]
    val bandeauImageFormat = properties[Key("images.bandeauImageFormat", stringType)]
    val bandeauFilenameSuffix = properties[Key("images.bandeauFilenameSuffix", stringType)]
    val screenshotsArchiveSubPath = properties[Key("images.screenshotsArchiveSubPath", stringType)]
}
