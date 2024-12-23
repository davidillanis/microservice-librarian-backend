package org.microservice.users.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsua;

    @Column(columnDefinition = "CHAR(25)", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(length = 15)
    @Size(min = 8, max = 15, message = "El telefono debe tener de 8 a 15 caracteres")
    private String teleUsua;

    @Column(columnDefinition = "CHAR(10)", nullable = false, unique = true)
    @NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 8, max = 10, message = "El DNI debe tener de 8 a 10 caracteres")
    private String DNIUsua;

    @Column(nullable = false, length = 45)
    @NotNull(message = "La contraseña no puede ser nula")
    private String nombUsua;

    @Column(columnDefinition = "CHAR(25)", nullable = false)
    private String apelPaternoUsua;

    @Column(columnDefinition = "CHAR(25)", nullable = false)
    private String apelMaternoUsua;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles=new HashSet<>();

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentEntity studentEntity;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private LibrarianEntity librarianEntity;
}
