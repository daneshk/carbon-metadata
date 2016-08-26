package org.wso2.carbon.metadata.base.store.services;

import org.wso2.carbon.metadata.base.store.exceptions.MetadataStoreException;

public interface StorageService<Input, Output> {

    /**
     * Method to add a resource to the metadata store
     *
     * @param input Implementation of the resource that needs to be added
     * @return uuid of the resource
     * @throws MetadataStoreException throws if operation is failed
     */
    String add(Input input) throws MetadataStoreException;

    /**
     * delete a resource from the metadata store
     *
     * @param uuid UUID of the resource that needs to be deleted
     * @throws MetadataStoreException throws if the operation failed
     */
    void delete(String uuid) throws MetadataStoreException;

    /**
     * Update a resource given the UUID
     *
     * @param input new resource to replace the existing resource
     * @throws MetadataStoreException
     */

    void update(String uuid, Input input) throws MetadataStoreException;

    /**
     * Method to retrieve resources given the uuid
     *
     * @param uuid UUID of the resource that needs to be retrieved
     * @return output to be retrieved
     * @throws MetadataStoreException throws if the operation failed
     */
    Output get(String uuid) throws MetadataStoreException;
}
