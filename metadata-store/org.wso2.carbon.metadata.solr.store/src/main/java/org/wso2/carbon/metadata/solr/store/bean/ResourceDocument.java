package org.wso2.carbon.metadata.solr.store.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ResourceDocument {

    private String uuid;
    private int tenantId;
    private Map<String, Object> Fields;
    private Map<String, Collection<String>> multivaluedFields;

    public ResourceDocument(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public Map<String, Object> getFields() {
        return Fields;
    }

    public void setFields(Map<String, Object> fields) {
        Fields = fields;
    }

    public Map<String, Collection<String>> getMultivaluedFields() {
        return multivaluedFields;
    }

    public void setMultivaluedFields(Map<String, Collection<String>> multivaluedFields) {
        this.multivaluedFields = multivaluedFields;
    }
}
