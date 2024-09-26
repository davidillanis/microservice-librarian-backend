package org.microservice.librarian.model.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class CopyBookEntity {
    @Id
    @Column(columnDefinition = "CHAR(25)")
    private String codiEjem;

    @Column(length = 25, nullable = false)
    private String locaEjem;

    @Column(length = 25, nullable = false)
    private String estaEjem;

    @Column(nullable = false)
    private boolean habiEjem;

    @ManyToOne
    @JoinColumn(name = "idLibr", nullable = false)
    private BookEntity bookEntity;

    @OneToMany(targetEntity = RequestEntity.class, cascade = CascadeType.ALL, mappedBy = "copyBookEntity")
    private List<RequestEntity> requestEntities;

    @OneToMany(targetEntity = LoanEntity.class, cascade = CascadeType.ALL, mappedBy = "copyBookEntity")
    private List<LoanEntity> loanEntities;
}