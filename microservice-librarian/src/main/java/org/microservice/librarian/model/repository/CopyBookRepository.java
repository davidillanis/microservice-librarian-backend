package org.microservice.librarian.model.repository;


import org.microservice.librarian.model.entity.CopyBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyBookRepository extends JpaRepository<CopyBookEntity, String> {
}