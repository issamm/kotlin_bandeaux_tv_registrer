package dev.issam.bandeauxtv.registrer.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.connection.ConnectionPoolSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.issam.bandeauxtv.registrer.properties.MongodbProperties
import java.util.concurrent.TimeUnit

class MongoDbClient(val mongoDbProperties: MongodbProperties) {

    fun db(): MongoDatabase {
        val mongoClient = mongodbConnection(mongoDbProperties)
        return mongoClient.getDatabase(databaseName = mongoDbProperties.databaseName)
    }

    private fun mongodbConnection(mongoDbProperties: MongodbProperties): MongoClient {
        // Replace the placeholders with your credentials and hostname
        val connectionString =
            "mongodb://${mongoDbProperties.url}:${mongoDbProperties.port}/${mongoDbProperties.databaseName}"
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val b = ConnectionPoolSettings.builder()
        b.maxConnectionIdleTime(5, TimeUnit.MINUTES)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .applyToConnectionPoolSettings { builder ->
                builder.maxConnectionIdleTime(5, TimeUnit.MINUTES)
                builder.maxConnectionLifeTime(5, TimeUnit.MINUTES)
            }
            .credential(MongoCredential.createCredential(mongoDbProperties.username, mongoDbProperties.databaseName, mongoDbProperties.password.toCharArray()))
            .serverApi(serverApi)
            .build()
        val mongoClient: MongoClient = MongoClient.create(mongoClientSettings)
        /*
        mongoClient.use { mongoClient ->
            val database = mongoClient.getDatabase("bandeauxtv")
            runBlocking {
                database.runCommand(Document("ping", 1))
                mongoClient.listDatabaseNames().collect { dbName -> println(dbName) }
            }
            println("Pinged your deployment. You successfully connected to MongoDB!")
        }
         */
        return mongoClient
    }

}
