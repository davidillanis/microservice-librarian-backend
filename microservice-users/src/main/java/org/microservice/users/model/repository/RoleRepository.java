package org.microservice.users.model.repository;

import org.microservice.users.model.entity.RoleEntity;
import org.microservice.users.utils.other.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findRoleEntityByRole(ERole role);
    Boolean existsByRole(ERole role);
}
