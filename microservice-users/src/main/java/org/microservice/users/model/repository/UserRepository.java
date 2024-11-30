package org.microservice.users.model.repository;

import jakarta.transaction.Transactional;
import org.microservice.users.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByDNIUsua(String dniUsua);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.username = :username")
    int updatePasswordByUsername(String username, String password);

}
