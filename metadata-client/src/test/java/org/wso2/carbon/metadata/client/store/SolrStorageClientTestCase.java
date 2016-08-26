package org.wso2.carbon.metadata.client.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.wso2.carbon.metadata.base.model.bean.SoapService;
import org.wso2.carbon.metadata.base.store.builder.StorageServiceBuilderFactory;
import org.wso2.carbon.metadata.base.store.exceptions.MetadataStoreException;
import org.wso2.carbon.metadata.base.store.services.StorageService;
import org.testng.annotations.Test;


public class SolrStorageClientTestCase {

    private static final Log log = LogFactory.getLog(SolrStorageClientTestCase.class);
    private String assetID = null;
    private StorageService storageService = null;


    @BeforeClass
    public void initialize() {
        System.out.println("calling solr storage client initialize test case");
        try {
            StorageServiceBuilderFactory storageServiceBuilderFactory = new StorageServiceBuilderFactory();
            storageService = storageServiceBuilderFactory.getStorageService();
        } catch (MetadataStoreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAssetCreate() {
        System.out.println("calling solr storage client asset create test case");
        try {
            SoapService soapService = new SoapService();
            soapService.setName("WeatherService");
            soapService.setMediaType("application/vnd.soapservice");
            soapService.setDescription("testing storage service");
            assetID = storageService.add(soapService);
            System.out.println("asset created successfully ::: assetID: " +assetID);
            Assert.assertNotNull(assetID, "assetID should not be null");
        } catch (MetadataStoreException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = { "testAssetCreate" })
    public void testAssetGet() {
        System.out.println("calling solr storage client get asset test case");
        try {
            storageService.get(assetID);
        } catch (MetadataStoreException e) {
            e.printStackTrace();
        }
    }
}
