package org.wso2.carbon.metadata.base.store.builder;

import org.wso2.carbon.metadata.base.store.exceptions.MetadataStoreException;
import org.wso2.carbon.metadata.base.store.services.StorageService;

public class StorageServiceBuilderFactory {

    private static StorageService storageService = null;

    public StorageService getStorageService() throws MetadataStoreException {
        if (storageService == null) {
            storageService = generateStorageServiceInstance();
        }
        return storageService;
    }

    private StorageService generateStorageServiceInstance() throws MetadataStoreException {
        try {
            Class o = Class.forName("org.wso2.carbon.metadata.client.store.services.SolrStorageTypeService");
            return storageService = (StorageService)o.newInstance();
        } catch (ClassNotFoundException e) {
            throw new MetadataStoreException("Error while generating solr client instance", e);
        } catch (InstantiationException e) {
            throw new MetadataStoreException("Error while generating solr client instance", e);
        } catch (IllegalAccessException e) {
            throw new MetadataStoreException("Error while generating solr client instance", e);
        }
    }
}
