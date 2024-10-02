package org.microservice.users.service.implementation;

import org.microservice.users.service.UserRoleService;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.users.utils.other.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddDataServiceImpl {
    @Autowired
    private UserRoleService userRoleService;

    public void add(){
        addUserRole();
    }

    public void addUserRole(){
        List<AuthCreateUserRequestDTO> listUsers=List.of(
                new AuthCreateUserRequestDTO("david","1234","David Abel","Illanis","Huaraca","12345678", "93298462", List.of(ERole.STUDENT)),
                new AuthCreateUserRequestDTO("romel","1234","Romel","Serna","Berrocal","81239812", "93212233", List.of(ERole.STUDENT)),
                new AuthCreateUserRequestDTO("eduard","1234","Eduard","Garcia","Berrocal","38725192", "932132133", List.of(ERole.STUDENT)),
                new AuthCreateUserRequestDTO("admin","1234","Admin","Lopez","Berrocal","38725193", "932132323", List.of(ERole.LIBRARIAN))
        );
        listUsers.forEach(user->userRoleService.createEntity(user));
    }
}
