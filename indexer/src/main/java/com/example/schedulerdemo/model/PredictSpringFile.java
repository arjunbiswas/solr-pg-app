package com.example.schedulerdemo.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "predictspring")
public class PredictSpringFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "received_on")
    private Timestamp timestamp;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    private byte[] data;

    @Column(name = "indexed")
    private Boolean indexed;

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

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIndexed() {
        return indexed;
    }

    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }
}