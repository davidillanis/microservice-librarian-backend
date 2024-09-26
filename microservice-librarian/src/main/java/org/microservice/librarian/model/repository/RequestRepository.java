package org.microservice.librarian.model.repository;


import org.microservice.librarian.model.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
}