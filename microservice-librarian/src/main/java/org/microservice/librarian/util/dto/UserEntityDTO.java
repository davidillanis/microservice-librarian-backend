package org.microservice.librarian.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityDTO {
    private Integer idUsua;
    private String username;
    private String password;
    private Boolean isEnabled;
    private String teleUsua;
    private String DNIUsua;
    private String nombUsua;
    private String apelPaternoUsua;
    private String apelMaternoUsua;
//    private Set<RoleEntity> roles=new HashSet<>();
//    private StudentEntity studentEntity;
//    private LibrarianEntity librarianEntity;
}
