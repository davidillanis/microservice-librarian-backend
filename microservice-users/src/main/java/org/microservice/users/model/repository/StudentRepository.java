package org.microservice.users.model.repository;

import org.microservice.users.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    //Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<StudentEntity> findStudentEntityByUserEntityUsername(String username);
}
