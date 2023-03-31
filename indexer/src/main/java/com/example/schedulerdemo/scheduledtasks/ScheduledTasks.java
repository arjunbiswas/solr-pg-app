package com.example.schedulerdemo.scheduledtasks;

import com.example.schedulerdemo.config.SolrjClient;
import com.example.schedulerdemo.model.PredictSpringFile;
import com.example.schedulerdemo.model.SolrPredictSpringDocument;
import com.example.schedulerdemo.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ConfigurationProperties(prefix = "solrj")
public class ScheduledTasks {

    @Value("${solrj.batchSize}")
    private int batchSize;

    @Autowired
    FileStorageService storageService;

    @Autowired
    private SolrjClient solrjClient;

    public static final String REGEX_TAB_DELIMITER = "\t";

    @Scheduled(fixedRate = 600000)
    public void indexLatestPredictSpringFiles() throws IOException {
        log.info("Fetching latest files ");
        List<PredictSpringFile> nonIndexedFiles = storageService.getAllNonIndexedFiles();
        for (PredictSpringFile predictSpringFile : nonIndexedFiles) {
            PredictSpringFile fileForIndexing = storageService.getFileForIndexing(predictSpringFile.getId());
            log.info("Caching latest file ");
            File cacheFile = new File(System.getProperty("user.dir") + "/indexer/indexing-queue/" + fileForIndexing.getId());
            if (cacheFileLocallyForStreaminRead(fileForIndexing, cacheFile)) {
                try {
                    processFile(cacheFile);
                    storageService.updateFileAfterIndexing(fileForIndexing);
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * cacheFileLocallyForStreaminRead
     *
     * @param fileForIndexing
     * @param cacheFile
     * @return
     */
    private boolean cacheFileLocallyForStreaminRead(PredictSpringFile fileForIndexing, File cacheFile) {
        boolean cached = false;
        try (FileOutputStream fos = new FileOutputStream(cacheFile)) {
            fos.write(fileForIndexing.getData());
            fos.flush();
            cached = true;
        } catch (IOException e) {
            log.error("Received exception when writing file {}.  Attempting delete {}", cacheFile.getName(), e);
            try {
                cacheFile.delete();
            } catch (Exception deleteException) {
                log.error("Received exception when deleting file {}.", cacheFile.getName(), deleteException);
            }
            cached = false;
        }
        return cached;
    }

    /**
     * processFile
     *
     * @param cacheFile
     * @throws SolrServerException
     * @throws IOException
     */
    private void processFile(File cacheFile) throws SolrServerException, IOException {
        List<SolrPredictSpringDocument> solrDocumentBatch = new ArrayList<>();
        LineIterator it = FileUtils.lineIterator(cacheFile, "UTF-8");
        String[] columns = it.nextLine().split(REGEX_TAB_DELIMITER);
        int lineNumStart = 1;
        int lineNumEnd = 1;
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                SolrPredictSpringDocument document = getSolrInputDocument(line, columns);
                lineNumEnd++;
                solrDocumentBatch.add(document);
                if (lineNumEnd % batchSize == 0) {
                    try {
                        addDocumentsToSolr(solrDocumentBatch, lineNumStart, lineNumEnd);
                        lineNumStart = lineNumEnd;
                        solrDocumentBatch = new ArrayList<>();
                    } catch (HttpSolrClient.RemoteSolrException e) {
                        log.warn("Index failed for lines {} - {}, message {} ", lineNumStart, lineNumEnd, e.getMessage());
                        solrDocumentBatch = new ArrayList<>();
                    }
                }
            }
        } finally {

        }
    }


    /**
     * addDocumentsToSolr
     *
     * @param solrDocumentBatch
     * @param lineNumStart
     * @param lineNumEnd
     * @throws SolrServerException
     * @throws IOException
     */
    private void addDocumentsToSolr(List<SolrPredictSpringDocument> solrDocumentBatch, int lineNumStart, int lineNumEnd) throws SolrServerException, IOException {
        UpdateResponse add = solrjClient.getSolrjClient().addBeans(solrDocumentBatch);
        UpdateResponse commit = solrjClient.getSolrjClient().commit();
        log.info("Successfully indexed lines {} - {}", lineNumStart, lineNumEnd);
    }

    /**
     * getSolrInputDocument
     *
     * @param line
     * @param columns
     * @return
     */
    private SolrPredictSpringDocument getSolrInputDocument(String line, String[] columns) {
        SolrPredictSpringDocument solrPredictSpringDocument = new SolrPredictSpringDocument();


        String[] tokens = line.split(REGEX_TAB_DELIMITER);
        solrPredictSpringDocument.setSku_id(tokens[0]);
        solrPredictSpringDocument.setImage(tokens[1]);
        solrPredictSpringDocument.setAdditional_image_link(tokens[2]);
        solrPredictSpringDocument.setStyle_id(tokens[3]);
        solrPredictSpringDocument.setClass_id(tokens[4]);
        solrPredictSpringDocument.setColor(tokens[5]);
        solrPredictSpringDocument.setColor_code(tokens[6]);
        solrPredictSpringDocument.setColor_family(tokens[7]);
        solrPredictSpringDocument.setSize(tokens[8]);
        solrPredictSpringDocument.setSize_id(tokens[9]);
        solrPredictSpringDocument.setDepartment_id(tokens[10]);
        solrPredictSpringDocument.setDissection_code(tokens[11]);
        solrPredictSpringDocument.setHazmat(tokens[12]);
        solrPredictSpringDocument.setRedline(tokens[13]);
        solrPredictSpringDocument.setPromoted(tokens[14]);
        solrPredictSpringDocument.setTax_code(tokens[15]);
        solrPredictSpringDocument.setVendor(tokens[16]);
        solrPredictSpringDocument.setList_price(tokens[17]);
        solrPredictSpringDocument.setSale_price(tokens[18]);
        solrPredictSpringDocument.setSale_price_effective_date(tokens[19]);
        solrPredictSpringDocument.setCurrency(tokens[20]);
        solrPredictSpringDocument.setShoprunner_eligible(tokens[21]);
        solrPredictSpringDocument.setTitle(tokens[22]);
        solrPredictSpringDocument.setLink(tokens[23]);
        solrPredictSpringDocument.setProd_description(tokens[24]);
        solrPredictSpringDocument.setStart_date(tokens[25]);
        solrPredictSpringDocument.setFeatured_color(tokens[26]);
        solrPredictSpringDocument.setFeatured_color_category(tokens[27]);
        solrPredictSpringDocument.setRelated_products(tokens[28]);
        solrPredictSpringDocument.setPre_order(tokens[29]);
        solrPredictSpringDocument.setHandling_code(tokens[30]);
        solrPredictSpringDocument.setVideo(tokens[31]);
        solrPredictSpringDocument.setProportion(tokens[32]);
        solrPredictSpringDocument.setProportion_products(tokens[33]);
        solrPredictSpringDocument.setMaster_style(tokens[34]);
        solrPredictSpringDocument.setCannot_return(tokens[35]);
        solrPredictSpringDocument.setGreat_find(tokens[36]);
        solrPredictSpringDocument.setWeb_exclusive(tokens[37]);
        solrPredictSpringDocument.setNy_deals(tokens[38]);
        solrPredictSpringDocument.setPromo_tagline(tokens[39]);
        solrPredictSpringDocument.setPartially_promoted(tokens[40]);
        solrPredictSpringDocument.setProduct_category(tokens[41]);
        solrPredictSpringDocument.setSort_order(tokens[42]);
        solrPredictSpringDocument.setQuantity_sold(tokens[43]);
        if (tokens.length == 45) {
            solrPredictSpringDocument.setAverage_rating(String.valueOf(tokens[44]));
        } else {
            solrPredictSpringDocument.setAverage_rating("0.0");
        }
        return solrPredictSpringDocument;
    }
}
