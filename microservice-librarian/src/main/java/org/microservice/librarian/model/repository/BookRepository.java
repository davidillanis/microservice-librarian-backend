package org.microservice.librarian.model.repository;

import org.microservice.librarian.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Optional<BookEntity> findBookEntityByIsbnLibr(String isbn);

    @Query("SELECT b FROM LoanEntity l " +
            "JOIN l.copyBookEntity c " +
            "JOIN c.bookEntity b " +
            "WHERE c.habiEjem = true " +
            "GROUP BY b " +
            "HAVING COUNT(l) > 0 " +
            "ORDER BY COUNT(l) DESC")
    List<BookEntity> getListPopularCopyBook();

}