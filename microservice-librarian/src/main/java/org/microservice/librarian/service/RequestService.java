package org.microservice.librarian.service;


import org.microservice.librarian.model.entity.RequestEntity;

import java.util.List;

public interface RequestService {
    void createEntity(RequestEntity obj);
    List<RequestEntity> getListEntity();
    RequestEntity getEntity(Integer id);
    void updateEntity(RequestEntity obj);
    void deleteEntity(Integer id);
}