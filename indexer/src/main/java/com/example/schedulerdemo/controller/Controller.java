package com.example.schedulerdemo.controller;


// Importing required classes

import com.example.schedulerdemo.IndexingResponse;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin("http://localhost:8081")
public class Controller {

    @GetMapping("v1/jobs/{id}")
    public ResponseEntity<IndexingResponse> getJobStatus(
            @RequestPart("file") MultipartFile multipartFile)
            throws IOException {

        IndexingResponse response = new IndexingResponse();
        response.setFileName("fileName");
        response.setSize(multipartFile.getSize());
        response.setDownloadUri("/indexing/" + "fileCode");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
