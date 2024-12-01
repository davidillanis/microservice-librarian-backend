package org.microservice.users.service;

import org.microservice.users.model.entity.LibrarianEntity;
import org.microservice.users.model.entity.StudentEntity;
import org.microservice.users.model.entity.UserEntity;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.users.utils.dto.AuthUpdateUserRequestDTO;

import java.util.List;

public interface UserRoleService {
    List<UserEntity> getListUserEntity();
    UserEntity getUserEntityById(Integer id);
    UserEntity getUserEntityByUsername(String username);
    StudentEntity getStudentByUsername(String username);
    StudentEntity getStudentById(Integer id);
    LibrarianEntity getLibrarianById(Integer id);
    void createEntity(AuthCreateUserRequestDTO obj) throws Exception;
    Boolean updateEntity(AuthUpdateUserRequestDTO obj);
    Boolean updateEnabledAccount(Integer id, Boolean enabled);
    void updatePassword(String username, String oldPassword, String newPassword);
    void updatePassword(String username, String Password);
    Boolean deleteEntity(Integer id);
}
