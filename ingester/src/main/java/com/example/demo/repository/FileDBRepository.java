package com.example.demo.repository;

import com.example.demo.model.PredictSpringFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileDBRepository extends JpaRepository<PredictSpringFile, String> {

}