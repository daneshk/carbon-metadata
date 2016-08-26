package org.wso2.carbon.metadata.solr.store.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;

public class HTTPSolrClient extends AbstractSolrClient {
    private static final Log log = LogFactory.getLog(SolrClient.class);

    public HTTPSolrClient() throws IOException {
        super();
    }

    public void initialize() throws IOException {
        String solrServerUrl = "http://localhost:8983/solr/registry-indexing";
        this.solrClient = new HttpSolrClient.Builder(solrServerUrl).build();
        log.info("Http Sorl server initiated at: " + solrServerUrl);
    }
}
