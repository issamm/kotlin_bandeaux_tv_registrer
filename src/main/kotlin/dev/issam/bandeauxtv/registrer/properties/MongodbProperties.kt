package dev.issam.bandeauxtv.registrer.properties

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType

class MongodbProperties(properties: ConfigurationProperties) {
    val url = properties[Key("mongodb.url", stringType)]
    val port = properties[Key("mongodb.port", intType)]
    val databaseName = properties[Key("mongodb.databaseName", stringType)]
    val username = properties[Key("mongodb.username", stringType)]
    val password = properties[Key("mongodb.password", stringType)]
}
