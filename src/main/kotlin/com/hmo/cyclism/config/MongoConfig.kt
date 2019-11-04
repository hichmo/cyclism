package com.hmo.cyclism.config

import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory


@Configuration
class MongoConfig(

        @Value("\${spring.data.mongodb.uri}")
        var uri: String,
        @Value("\${spring.data.mongodb.database}")
        var databaseName: String
) {
    @Bean
    fun reactiveMongoClient():
            ReactiveMongoDatabaseFactory = SimpleReactiveMongoDatabaseFactory(MongoClients.create(uri), databaseName);
}