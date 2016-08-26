package org.wso2.carbon.metadata.solr.store.builder;

import org.wso2.carbon.metadata.base.store.exceptions.MetadataStoreException;
import org.wso2.carbon.metadata.solr.store.client.SolrClient;

public class SolrClientBuilderFactory {

    private static SolrClient solrClient = null;

    public SolrClient getSolrClient() throws MetadataStoreException {
        if (solrClient == null) {
            solrClient = generateSolrClientInstance();
        }
        return solrClient;
    }

    private SolrClient generateSolrClientInstance() throws MetadataStoreException {
        try {
            Class o = Class.forName("org.wso2.carbon.metadata.solr.store.client.EmbeddedSolrClient");
            return solrClient = (SolrClient)o.newInstance();
        } catch (ClassNotFoundException e) {
            throw new MetadataStoreException("Error while generating solr client instance", e);
        } catch (InstantiationException e) {
            throw new MetadataStoreException("Error while generating solr client instance", e);
        } catch (IllegalAccessException e) {
            throw new MetadataStoreException("Error while generating solr client instance", e);
        }
    }
}
