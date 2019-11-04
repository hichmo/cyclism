package com.hmo.cyclism.config

import com.hmo.cyclism.model.Cyclist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Component


@Component
class DataImportConfiguration(@Autowired val mongo: ReactiveMongoOperations) : CommandLineRunner {
    override fun run(vararg args: String?) {
 
        mongo.collectionExists(Cyclist::class.java)
                .doOnNext { col ->
                    if (!col)
                        mongo.createCollection(Cyclist::class.java).subscribe()
                    getCyclists().forEach { c -> mongo.save(c).subscribe() }


                }.subscribe()


    }

    fun getCyclists(): List<Cyclist> {
        return listOf(
                Cyclist("", "Chris Froome", "Ineos"),
                Cyclist("", "Vincenzo Nibali", "Bahrain Merida"),
                Cyclist("", "Thibaut Pinot", "Groupama FDJ"),
                Cyclist("", "Richie Porte", "Trek Segafredo"),
                Cyclist("", "Primoz Roglic", "Jumbo Visma")
        )
    }

}