package com.example.demo.controller;

import com.example.demo.messages.ResponseFile;
import com.example.demo.messages.ResponseMessage;
import com.example.demo.model.PredictSpringFile;
import com.example.demo.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("http://localhost:8080")
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/v1/upload")
    public ResponseEntity<ResponseMessage> uploadFile(
            @RequestPart("file") MultipartFile file)
            throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String message = "";
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            PredictSpringFile store = storageService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            responseMessage.setFileName(fileName);
            responseMessage.setSize(file.getSize());
            responseMessage.setDownloadUri("/v1/files/" + store.getId());
            responseMessage.setMessage(message);
            responseMessage.setFileReceivedTimeStamp(store.getTimestamp());
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            responseMessage.setFileName(fileName);
            responseMessage.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMessage);
        }
    }

    @GetMapping("/v1/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getTimestamp());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/v1/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id, @RequestParam(required = false) boolean data) {
        PredictSpringFile fileDB = storageService.getFile(id);
        if (data) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                    .body(fileDB.getData());
        } else {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                    .body(fileDB.toString().getBytes(StandardCharsets.UTF_8));
        }
    }
}
