package org.microservice.librarian.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSoli;

    @Column(nullable = false)
    private LocalDate fechSoli;

    @Column(nullable = false, length = 25)
    private String EstaSoli;

/*  @ManyToOne
    @JoinColumn(name = "idAlumn", nullable = false)
    private StudentEntity studentEntity;*/

    @ManyToOne
    @JoinColumn(name = "codiEjem", nullable = false)
    private CopyBookEntity copyBookEntity;
}