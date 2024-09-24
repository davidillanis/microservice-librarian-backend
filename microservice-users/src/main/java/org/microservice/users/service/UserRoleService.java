package org.microservice.users.service;

import org.microservice.users.model.entity.LibrarianEntity;
import org.microservice.users.model.entity.StudentEntity;
import org.microservice.users.model.entity.UserEntity;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;

public interface UserRoleService {
    UserEntity getUserEntityById(Integer id);
    UserEntity getUserEntityByUsername(String username);
    StudentEntity getStudentById(Integer id);
    LibrarianEntity getLibrarianById(Integer id);
    Boolean createEntity(AuthCreateUserRequestDTO obj);
    Boolean updateEntity(AuthCreateUserRequestDTO obj);
    Boolean updateEnabledAccount(Integer id, Boolean enabled);
    Boolean deleteEntity(Integer id);
}
