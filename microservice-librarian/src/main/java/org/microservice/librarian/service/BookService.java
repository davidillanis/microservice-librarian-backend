package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.BookEntity;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.util.http.request.BookIssueRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {
    //create, read, update, delete
    boolean createEntity(BookEntity obj);
    List<BookEntity> getListEntity();
    BookEntity getEntity(Integer id);
    boolean updateEntity(BookEntity obj);
    boolean deleteEntity(Integer id);
    Optional<BookEntity> findBookEntityByIsbnLibr(String isbn);

    //others
    Boolean createBookIssue(BookIssueRequest obj);
    List<BookEntity> getListPopularCopyBook();
    CopyBookEntity searchCopyBookAvailable(String isbn);
}