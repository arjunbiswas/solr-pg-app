package com.example.schedulerdemo.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="solrj")
public class SolrjClient {

    private String solrUrl;
    private String solrjCore;

    @Bean
    public HttpSolrClient getSolrjClient() {
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl + solrjCore).build();
        client.setParser(new XMLResponseParser());
        return client;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public void setSolrUrl(String solrUrl) {
        this.solrUrl = solrUrl;
    }

    public String getSolrjCore() {
        return solrjCore;
    }

    public void setSolrjCore(String solrjCore) {
        this.solrjCore = solrjCore;
    }
}