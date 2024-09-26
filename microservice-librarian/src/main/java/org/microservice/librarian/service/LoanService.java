package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.LoanEntity;

import java.util.List;

public interface LoanService {
    boolean createEntity(LoanEntity obj);
    List<LoanEntity> getListEntity();
    LoanEntity getEntity(Integer id);
    boolean updateEntity(LoanEntity obj);
    boolean deleteEntity(Integer id);
}