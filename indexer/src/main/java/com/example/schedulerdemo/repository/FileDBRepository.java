package com.example.schedulerdemo.repository;

import com.example.schedulerdemo.model.PredictSpringFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface FileDBRepository extends JpaRepository<PredictSpringFile, String> {

    @Query(value = "select ps.id, ps.name from predictspring ps where ps.indexed != true",nativeQuery = true)
    List<Object[]> getPredictSpringFileIdandName();

}