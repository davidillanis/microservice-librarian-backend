package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.CopyBookEntity;

import java.util.List;

public interface CopyBookService {
    //create, read, update, delete
    void createEntity(CopyBookEntity obj);
    List<CopyBookEntity> getListEntity();
    CopyBookEntity getEntity(String id);
    void updateEntity(CopyBookEntity obj);
    void deleteEntity(String id);

}