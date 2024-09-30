package org.microservice.librarian.model.repository;


import org.microservice.librarian.model.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {
    Optional<LoanEntity> findTopByCopyBookEntity_CodiEjemOrderByIdPrestDesc(String codiEjem);
}
