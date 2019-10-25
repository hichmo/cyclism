package com.hmo.cyclism.config;

import com.hmo.cyclism.model.Cyclist;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataImportConfiguration {

    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) -> {
            mongo.dropCollection(Cyclist.class);
            mongo.createCollection(Cyclist.class);
            getCyclists().forEach(mongo::save);
        };
    }

    private List<Cyclist> getCyclists() {
        return Arrays.asList(
                new Cyclist("Chris Froome", "Ineos"),
                new Cyclist("Vincenzo Nibali", "Bahrain Merida"),
                new Cyclist("Thibaut Pinot", "Groupama FDJ"),
                new Cyclist("Richie Porte", "Trek Segafredo"),
                new Cyclist("Primoz Roglic", "Jumbo Visma"));
    }
}
