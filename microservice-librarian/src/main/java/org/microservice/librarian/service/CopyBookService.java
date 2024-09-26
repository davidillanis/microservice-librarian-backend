package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.CopyBookEntity;

import java.util.List;

public interface CopyBookService {
    //create, read, update, delete
    boolean createEntity(CopyBookEntity obj);
    List<CopyBookEntity> getListEntity();
    CopyBookEntity getEntity(String id);
    boolean updateEntity(CopyBookEntity obj);
    boolean deleteEntity(String id);

}