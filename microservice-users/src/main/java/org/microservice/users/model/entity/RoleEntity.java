package org.microservice.users.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.microservice.users.utils.other.ERole;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(nullable = false, unique = true, length = 50)
    @Column(columnDefinition = "CHAR(25)", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ERole role;
}
