package org.wso2.carbon.metadata.solr.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.testng.annotations.Test;
import org.wso2.carbon.metadata.base.store.exceptions.MetadataStoreException;
import org.wso2.carbon.metadata.solr.store.bean.ResourceDocument;
import org.wso2.carbon.metadata.solr.store.builder.SolrClientBuilderFactory;
import org.wso2.carbon.metadata.solr.store.client.SolrClient;

import java.io.IOException;
import java.util.HashMap;

public class SolrClientTestCase {
    private static final Log log = LogFactory.getLog(SolrClientTestCase.class);
    @Test
    public void testInitializeClient() {
        System.out.println("calling initialize solr client test case");
        log.info("calling initialize solr client test case");
        SolrClientBuilderFactory factory = new SolrClientBuilderFactory();
        try {
            SolrClient client = factory.getSolrClient();
            ResourceDocument doc = new ResourceDocument("12222222350");
            HashMap stringFields = new HashMap ();
            stringFields.put("name", "danesh");
            doc.setFields(stringFields);
            client.addDocuments(doc);
            ResourceDocument document = client.get("12222222350", 1);
            System.out.println("Document ::: " + document.getFields().toString());
        } catch (MetadataStoreException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
