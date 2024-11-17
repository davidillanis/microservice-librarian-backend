package org.microservice.users.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlumn;

    @Column(nullable = false)
    private LocalDate fechRegiAlum;

    @Column(columnDefinition = "CHAR(10)", nullable = false)
    private String gradoYseccion;

    @Column(columnDefinition = "CHAR(25)", nullable = false)
    private String nivelEstudio;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsua", nullable = false)
    private UserEntity userEntity;
}
