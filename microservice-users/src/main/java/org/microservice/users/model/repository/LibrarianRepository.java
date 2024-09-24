package org.microservice.users.model.repository;

import org.microservice.users.model.entity.LibrarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<LibrarianEntity, Integer> {
}
