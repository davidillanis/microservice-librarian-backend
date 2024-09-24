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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsua", nullable = false)
    private UserEntity userEntity;
}
