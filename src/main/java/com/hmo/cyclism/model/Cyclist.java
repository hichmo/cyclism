package com.hmo.cyclism.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;


@Document("cyclists")
public class Cyclist {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String team;


    public Cyclist() {
    }

    public Cyclist(String name, String team) {
        this.name = name;
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cyclist cyclist = (Cyclist) o;
        return Objects.equals(name, cyclist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Cyclist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
