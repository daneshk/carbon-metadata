package org.wso2.carbon.metadata.solr.store.client;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.wso2.carbon.metadata.solr.store.bean.ResourceDocument;
import org.wso2.carbon.metadata.solr.store.utils.IndexingConstants;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractSolrClient implements SolrClient {

    public org.apache.solr.client.solrj.SolrClient solrClient = null;

    public AbstractSolrClient() throws IOException {
        this.initialize();
    }

    public abstract void initialize() throws IOException;

    public void addDocuments(ResourceDocument resourceDocument) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
/*        private String uuid;
        private int tenantId;
        private Map<String, String> stringFields;
        private Map<String, List<String>> multivaluedStringFields;
        private Map<String, Date> dateFields;
        private Map<String, Integer> intFields;
        private Map<String, List<Integer>> multivaluedIntFields;*/
        doc.addField(IndexingConstants.FIELD_ID, resourceDocument.getUuid());
        for(Map.Entry e: resourceDocument.getFields().entrySet()) {
            doc.addField(e.getKey() + IndexingConstants.SOLR_STRING_FIELD_KEY_SUFFIX, e.getValue());
        }
        if (solrClient != null) {
            solrClient.add(doc);
        }
    }

    public void query(String query, Map<String, String> fields, int tenantId) {

    }

    public void facetQuery(String query, Map<String, String> fields, int tenantId) {

    }

    public void deleteIndexByQuery(String query, int tenantId) {

    }

    public void deleteIndexByID(String uuid, int tenantId) {

    }

    public ResourceDocument get(String uuid, int tenantId) throws IOException, SolrServerException {
        if (solrClient == null) {
            System.out.println("ERR :: solr server not initialized");
            return null;
        }
        SolrQuery q = new SolrQuery();
        q.setRequestHandler("/get");
        q.set("id", uuid);
        q.set("fl", "*");
        QueryResponse response = solrClient.query(q);
        SolrDocument document = (SolrDocument)response.getResponse().get("doc");
        return getResourceDocument(document);
    }

    private ResourceDocument getResourceDocument(SolrDocument entries) {
        if (entries == null) {
            System.out.println("ERRrrr document is null");
            return null;
        }
        ResourceDocument document = new ResourceDocument((String)entries.get(IndexingConstants.FIELD_ID));
        document.setFields(entries.getFieldValueMap());
        return document;
    }


}
