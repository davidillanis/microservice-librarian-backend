package org.microservice.librarian.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLibr;

    @NotBlank(message = "this title not blank")
    @Column(length = 95, nullable = false)
    private String tituLibr;

    @NotBlank(message = "this isbn not blank")
    @NotNull(message = "not null")
    @Size(min = 10, max = 13, message = "This ISBN must have between 10 and 13 digits.")
    @Column(columnDefinition = "CHAR(13)", nullable = false, unique = true)
    private String isbnLibr;

    @Column(length = 95)
    private String descLibr;

    @Column(nullable = false)
    private LocalDate fechPublLibr;

    @NotBlank(message = "not blank")
    @NotNull(message = "not null")
    @Column(nullable = false, length = 45)
    private String editLibr;

    @NotBlank(message = "not blank")
    @NotNull(message = "not null")
    @Column(nullable = false, length = 45)
    private String autoLibr;

    @OneToMany(targetEntity = CopyBookEntity.class, cascade = CascadeType.ALL, mappedBy = "bookEntity")
    private List<CopyBookEntity> copyBookEntities;
}