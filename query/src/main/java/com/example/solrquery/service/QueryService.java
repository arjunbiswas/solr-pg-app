package com.example.solrquery.service;

import com.example.solrquery.config.SolrjClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryService {

    @Autowired
    private SolrjClient solrjClient;

    public SolrDocumentList search(String jsonString) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        HashMap<String,String> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, String>>(){}.getType());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("fulltext")) {
                query.setQuery(entry.getValue());
                break;
            } else {
                String solrQueryTerm = entry.getKey() + ":" + entry.getValue();
                query.addFilterQuery(solrQueryTerm);
                query.setStart(0);
                query.setRows(20);
            }
        }

        QueryResponse response = solrjClient.getSolrjClient().query(query);
        if (response != null) {
            return response.getResults();
        } else {
            return new SolrDocumentList();
        }
    }
}
