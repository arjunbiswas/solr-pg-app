package com.example.schedulerdemo.service;

import com.example.schedulerdemo.model.PredictSpringFile;
import com.example.schedulerdemo.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public PredictSpringFile store(PredictSpringFile predictSpringFile) throws IOException {
        return fileDBRepository.save(predictSpringFile);
    }

    public PredictSpringFile getFile(String id) {
        PredictSpringFile predictSpringFile = fileDBRepository.findById(id).get();
        return predictSpringFile;
    }

    public PredictSpringFile getFileForIndexing(String id) throws IOException {
        PredictSpringFile predictSpringFile = fileDBRepository.findById(id).get();
        return predictSpringFile;
    }

    public PredictSpringFile updateFileAfterIndexing(PredictSpringFile predictSpringFile) throws IOException {
        predictSpringFile.setIndexed(true);
        store(predictSpringFile);
        return predictSpringFile;
    }

    public Stream<PredictSpringFile> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public List<PredictSpringFile> getAllNonIndexedFiles() {
        List<PredictSpringFile> nonIndexedFilesList = new ArrayList<>();
        List<Object[]> nonIndexedFilesListDBResponse = fileDBRepository.getPredictSpringFileIdandName();
        for (Object[] record : nonIndexedFilesListDBResponse) {
            PredictSpringFile ps = new PredictSpringFile();
            ps.setId((String) record[0]);
            ps.setName((String) record[1]);
            nonIndexedFilesList.add(ps);
        }
        return nonIndexedFilesList;
    }
}
