package com.hmo.cyclism.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("cyclists")
public class Cyclist {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String team;
}
