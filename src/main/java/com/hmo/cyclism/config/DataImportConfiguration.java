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
                Cyclist.builder().name("Chris Froome").team("Ineos").build(),
                Cyclist.builder().name("Vincenzo Nibali").team("Bahrain Merida").build(),
                Cyclist.builder().name("Thibaut Pinot").team("Groupama FDJ").build(),
                Cyclist.builder().name("Richie Porte").team("Trek Segafredo").build(),
                Cyclist.builder().name("Primoz Roglic").team("Jumbo Visma").build());
    }
}
