package org.microservice.librarian.service;


import org.microservice.librarian.model.entity.RequestEntity;

import java.util.List;

public interface RequestService {
    boolean createEntity(RequestEntity obj);
    List<RequestEntity> getListEntity();
    RequestEntity getEntity(Integer id);
    boolean updateEntity(RequestEntity obj);
    boolean deleteEntity(Integer id);
}