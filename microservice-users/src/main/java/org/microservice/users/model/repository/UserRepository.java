package org.microservice.users.model.repository;

import org.microservice.users.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserEntityByUsername(String username);
}
