package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.entity.RequestEntity;

import java.util.List;

public interface LoanService {
    void createEntity(LoanEntity obj);
    List<LoanEntity> getListEntity();
    List<LoanEntity> getListEntityByUsername(String username);
    LoanEntity getEntity(Integer id);
    void updateEntity(LoanEntity obj);
    void deleteEntity(Integer id);
}