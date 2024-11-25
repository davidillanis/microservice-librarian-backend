package org.microservice.librarian.model.repository;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.microservice.librarian.model.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {

    Optional<Boolean> existsRequestEntityByCopyBookEntity_CodiEjemAndStudentEntityAndEstaSoli(
            @NotBlank(message = "not blank") String codiEjem,
            @NotNull(message = "not null") Integer studentEntity,
            @NotNull(message = "not null") String estaSoli);


}