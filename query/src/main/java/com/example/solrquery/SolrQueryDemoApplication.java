package com.example.solrquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolrQueryDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolrQueryDemoApplication.class, args);
	}
}
