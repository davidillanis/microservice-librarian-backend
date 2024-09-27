package org.microservice.librarian.controller;


import org.microservice.librarian.model.entity.BookEntity;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.service.BookService;
import org.microservice.librarian.util.http.request.BookIssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/library/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createBook(@RequestBody BookEntity bookEntity) {
        try {
            return new ResponseEntity<>(bookService.createEntity(bookEntity), HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/book-issue")
    public ResponseEntity<?> createBookIssue(@RequestBody BookIssueRequest bookIssueRequest) {
        return new ResponseEntity<>(bookService.createBookIssue(bookIssueRequest), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookEntity>> listBooks() {
        List<BookEntity> bookEntities=bookService.getListEntity().stream().map(bookEntity -> {
            bookEntity.getCopyBookEntities().stream().map(copyBook -> {
                copyBook.setBookEntity(null);
                copyBook.setRequestEntities(null);
                copyBook.setLoanEntities(null);
                return copyBook;
            }).collect(Collectors.toList());
            return bookEntity;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(bookEntities, HttpStatus.OK);
    }

    @GetMapping("/searchByISBN/{isbn}")
    public ResponseEntity<BookEntity> listBooksByISBN(@PathVariable("isbn") String isbn) {
        BookEntity bookEntity=bookService.findBookEntityByIsbnLibr(isbn).orElse(null);
        bookEntity.setCopyBookEntities(
                bookEntity.getCopyBookEntities().stream().map(copyBook -> {
                    copyBook.setBookEntity(null);
                    copyBook.setRequestEntities(null);
                    copyBook.setLoanEntities(null);
                    return copyBook;
                }).collect(Collectors.toList())
        );
        return new ResponseEntity<>(bookEntity, HttpStatus.OK);
    }

    @GetMapping("/search-copy-available/{isbn}")
    public ResponseEntity<CopyBookEntity> searchCopyBookAvailable(@PathVariable String isbn){
        CopyBookEntity copyBookEntity=bookService.searchCopyBookAvailable(isbn);
        copyBookEntity.setRequestEntities(null);
        copyBookEntity.setLoanEntities(null);
        copyBookEntity.setBookEntity(null);
        return new ResponseEntity<>(copyBookEntity, HttpStatus.OK);
    }

    @GetMapping("/get-list-popular")
    public ResponseEntity<List<BookEntity>> getPopularBooks() {
        List<BookEntity> bookEntities=bookService.getListPopularCopyBook().stream().map(bookEntity -> {
            bookEntity.getCopyBookEntities().stream().map(copyBookEntity -> {
                copyBookEntity.setBookEntity(null);
                copyBookEntity.setRequestEntities(null);
                copyBookEntity.setLoanEntities(null);
                return copyBookEntity;
            }).collect(Collectors.toList());
            return bookEntity;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(bookEntities, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateBook(@RequestBody BookEntity bookEntity) {
        try {
            return new ResponseEntity<>(bookService.updateEntity(bookEntity), HttpStatus.OK);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
