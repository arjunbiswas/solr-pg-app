package com.example.demo.model;

import javax.persistence.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.json.GsonJsonParser;

import java.sql.Timestamp;

@Entity
@Table(name = "predictspring")
public class PredictSpringFile {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "indexed")
    private Boolean indexed = false;

    @Column(name = "received_on")
    private Timestamp timestamp;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    private byte[] data;

    public PredictSpringFile() {
    }

    public PredictSpringFile(String name, Timestamp timestamp, String type, byte[] data) {
        this.name = name;
        this.timestamp = timestamp;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



    @Override
    public String toString() {
        PredictSpringFile p = new PredictSpringFile();
        p.id = this.getId();
        p.name = this.getName();
        p.timestamp = this.getTimestamp();
        p.type = this.getType();
        p.indexed = this.indexed;
        return gson.toJson(p);
    }
}