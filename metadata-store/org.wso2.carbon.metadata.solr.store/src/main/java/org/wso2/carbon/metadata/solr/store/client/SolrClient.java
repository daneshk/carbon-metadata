package org.wso2.carbon.metadata.solr.store.client;

import org.apache.solr.client.solrj.SolrServerException;
import org.wso2.carbon.metadata.solr.store.bean.ResourceDocument;

import java.io.IOException;
import java.util.Map;

public interface SolrClient {

    void initialize() throws IOException;

    void addDocuments(ResourceDocument resourceDocument) throws IOException, SolrServerException;

    void query(String query, Map<String,String> fields, int tenantId);

    void facetQuery(String query, Map<String,String> fields, int tenantId);

    ResourceDocument get(String uuid, int tenantId) throws IOException, SolrServerException;

    void deleteIndexByQuery(String query, int tenantId);

    void deleteIndexByID(String uuid, int tenantId);
}
