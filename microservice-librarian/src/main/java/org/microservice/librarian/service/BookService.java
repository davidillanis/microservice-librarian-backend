package org.microservice.librarian.service;

import org.microservice.librarian.model.entity.BookEntity;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.util.http.request.BookIssueRequest;

import java.util.List;

public interface BookService {
    //create, read, update, delete
    void createEntity(BookEntity obj);
    List<BookEntity> getListEntity();
    BookEntity getEntity(Integer id);
    void updateEntity(BookEntity obj);
    void deleteEntity(Integer id);
    BookEntity findBookEntityByIsbnLibr(String isbn);

    //others
    void createBookIssue(BookIssueRequest obj);
    List<BookEntity> getListPopularCopyBook();
    CopyBookEntity searchCopyBookAvailable(String isbn);
    List<CopyBookEntity> listCopyBookAvailable(String isbn);
}