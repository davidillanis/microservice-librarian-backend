package org.microservice.issue.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLibr;

    @Column(columnDefinition = "CHAR(13)", nullable = false, unique = true)
    private String isbnLibr;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = IssueEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "book_issue",
            joinColumns = @JoinColumn(name = "idLibr", referencedColumnName = "idLibr"),
            inverseJoinColumns = @JoinColumn(name = "idTema", referencedColumnName = "idTema"))
    private List<IssueEntity> issueEntities;

}