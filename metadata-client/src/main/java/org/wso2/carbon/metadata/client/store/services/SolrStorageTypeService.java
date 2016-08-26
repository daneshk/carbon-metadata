package org.wso2.carbon.metadata.client.store.services;

import org.apache.solr.client.solrj.SolrServerException;
import org.wso2.carbon.metadata.base.model.bean.Type;
import org.wso2.carbon.metadata.base.store.exceptions.MetadataStoreException;
import org.wso2.carbon.metadata.base.store.services.StorageService;
import org.wso2.carbon.metadata.solr.store.bean.ResourceDocument;
import org.wso2.carbon.metadata.solr.store.builder.SolrClientBuilderFactory;
import org.wso2.carbon.metadata.solr.store.client.SolrClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public class SolrStorageTypeService implements StorageService<Type, Type> {
    private SolrClient solrClient;

    public SolrStorageTypeService () {
        SolrClientBuilderFactory clientBuilderFactory = clientBuilderFactory = new SolrClientBuilderFactory();
        try {
            solrClient = clientBuilderFactory.getSolrClient();
        } catch (MetadataStoreException e) {
            e.printStackTrace();
        }
    }

    public String add(Type type) throws MetadataStoreException {
        String uuid = String.valueOf(UUID.randomUUID());
        ResourceDocument doc = new ResourceDocument(uuid);
        HashMap stringFields = new HashMap ();

        Class<?> typeClass = type.getClass();

        Field[] fields = typeClass.getFields();
        try {
            for(Field field : fields) {
                String name = field.getName();
                Object value = field.get(type);
                stringFields.put(name, value.toString());
                System.out.println(name + ": " + value.toString());
            }
        } catch (IllegalAccessException e) {
            throw new MetadataStoreException("" , e);
        }

        doc.setFields(stringFields);
        try {
            solrClient.addDocuments(doc);
            ResourceDocument document = solrClient.get(uuid, 1);
            return document.getUuid();
        } catch (IOException e) {
            throw new MetadataStoreException("" , e);
        } catch (SolrServerException e) {
            throw new MetadataStoreException("" , e);
        }
    }

    public void delete(String uuid) throws MetadataStoreException {

    }

    public void update(String uuid, Type type) throws MetadataStoreException {

    }

    public Type get(String uuid) throws MetadataStoreException {
        try {
            ResourceDocument document = solrClient.get(uuid, 1);
            System.out.println("Document ::: " + document.getFields().toString());
        } catch (IOException e) {
            throw new MetadataStoreException("" , e);
        } catch (SolrServerException e) {
            throw new MetadataStoreException("" , e);
        }
        return null;
    }
}
