package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.LoanEntity;

import java.util.List;

public interface LoanService {
    void createEntity(LoanEntity obj);
    List<LoanEntity> getListEntity();
    LoanEntity getEntity(Integer id);
    void updateEntity(LoanEntity obj);
    void deleteEntity(Integer id);
}