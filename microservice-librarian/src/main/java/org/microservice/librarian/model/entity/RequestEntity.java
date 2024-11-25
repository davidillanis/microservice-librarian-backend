package org.microservice.librarian.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "not null")
    @Column(nullable = false)
    private LocalDate fechSoli;

    @NotNull(message = "not null")
    @Column(nullable = false, length = 25)
    private String estaSoli;

    @NotNull(message = "not null")
    @Column(nullable = false)
    private Integer studentEntity;

/*  @ManyToOne
    @JoinColumn(name = "idAlumn", nullable = false)
    private StudentEntity studentEntity;*/

    @NotNull(message = "not null")
    @ManyToOne
    @JoinColumn(name = "codiEjem", nullable = false)
    private CopyBookEntity copyBookEntity;
}