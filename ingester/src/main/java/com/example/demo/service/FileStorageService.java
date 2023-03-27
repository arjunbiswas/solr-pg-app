package com.example.demo.service;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import com.example.demo.model.PredictSpringFile;
import com.example.demo.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public PredictSpringFile store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PredictSpringFile FileDB = new PredictSpringFile(fileName, Timestamp.from(ZonedDateTime.now().toInstant()),file.getContentType(), file.getBytes());
        return fileDBRepository.save(FileDB);
    }

    public PredictSpringFile getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<PredictSpringFile> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
