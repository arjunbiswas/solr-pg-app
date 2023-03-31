package com.example.solrquery.controller;

import com.example.solrquery.service.QueryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@CrossOrigin("http://localhost:8082")
public class Controller {

    @Autowired
    private QueryService queryService;

    private AtomicLong requestCount = new AtomicLong();

    @RequestMapping(value = "v1/query", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<SolrDocumentList> searchWithQuery(@RequestBody String jsonNode) {
        SolrDocumentList solrDocuments = null;
        try {
            solrDocuments = queryService.search(jsonNode);
            requestCount.addAndGet(1);
            log.info("server requestCount-" + requestCount +  ", resultset size : --> " + solrDocuments.size() + ", query : --> " + jsonNode);
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(solrDocuments);
        } catch (JsonProcessingException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(solrDocuments);
        } catch (SolrServerException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body(solrDocuments);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body(solrDocuments);
        }
    }
}
