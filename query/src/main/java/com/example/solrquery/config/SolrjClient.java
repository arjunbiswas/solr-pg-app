package com.example.solrquery.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "solrj")
public class SolrjClient {

    private String url;
    private String solrjCore;

    @Bean
    public HttpSolrClient getSolrjClient() {
        HttpSolrClient client = new HttpSolrClient.Builder(url + solrjCore).build();
        client.setParser(new XMLResponseParser());
        return client;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSolrjCore() {
        return solrjCore;
    }

    public void setSolrjCore(String solrjCore) {
        this.solrjCore = solrjCore;
    }
}