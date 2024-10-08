package org.microservice.librarian.model.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class CopyBookEntity {
    @NotBlank(message = "not blank")
    @Id
    @Column(columnDefinition = "CHAR(25)")
    private String codiEjem;

    @NotNull(message = "not null")
    @Column(length = 25, nullable = false)
    private String locaEjem;

    @NotNull(message = "not null")
    @NotBlank(message = "not blank")
    @Column(length = 25, nullable = false)
    private String estaEjem;

    @NotNull(message = "not null")
    @Column(nullable = false)
    private boolean habiEjem;

    @ManyToOne
    @JoinColumn(name = "idLibr", nullable = false)
    private BookEntity bookEntity;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = RequestEntity.class, cascade = CascadeType.ALL, mappedBy = "copyBookEntity")
    private List<RequestEntity> requestEntities;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = LoanEntity.class, cascade = CascadeType.ALL, mappedBy = "copyBookEntity")
    private List<LoanEntity> loanEntities;
}