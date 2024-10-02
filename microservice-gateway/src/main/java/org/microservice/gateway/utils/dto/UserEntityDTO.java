package org.microservice.gateway.utils.dto;


import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private List<RoleDTO> roles;
    /*private StudentEntity studentEntity;
    private LibrarianEntity librarianEntity;*/
}
