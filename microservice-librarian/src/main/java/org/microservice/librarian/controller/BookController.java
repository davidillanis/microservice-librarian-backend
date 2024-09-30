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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createBook(@RequestBody BookEntity bookEntity) {
        if (bookEntity == null || bookEntity.getIsbnLibr() == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        try {
            boolean result = bookService.createEntity(bookEntity);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/book-issue")
    public ResponseEntity<?> createBookIssue(@RequestBody BookIssueRequest bookIssueRequest) {
        if (bookIssueRequest == null || bookIssueRequest.getIsbnLibr() == null) {
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }
        try {
            boolean result = bookService.createBookIssue(bookIssueRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create book issue"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookEntity>> listBooks() {
        try {
            List<BookEntity> bookEntities = bookService.getListEntity().stream().map(bookEntity -> {
                bookEntity.getCopyBookEntities().forEach(copyBook -> {
                    copyBook.setBookEntity(null);
                    copyBook.setRequestEntities(null);
                    copyBook.setLoanEntities(null);
                });
                return bookEntity;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(bookEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByISBN/{isbn}")
    public ResponseEntity<?> listBooksByISBN(@PathVariable("isbn") String isbn) {
        try {
            Optional<BookEntity> bookOpt = bookService.findBookEntityByIsbnLibr(isbn);
            if (bookOpt.isEmpty()) {
                return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
            }
            BookEntity bookEntity = bookOpt.get();
            bookEntity.setCopyBookEntities(
                    bookEntity.getCopyBookEntities().stream().map(copyBook -> {
                        copyBook.setBookEntity(null);
                        copyBook.setRequestEntities(null);
                        copyBook.setLoanEntities(null);
                        return copyBook;
                    }).collect(Collectors.toList())
            );
            return new ResponseEntity<>(bookEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving book-"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search-copy-available/{isbn}")
    public ResponseEntity<?> searchCopyBookAvailable(@PathVariable String isbn) {
        try {
            CopyBookEntity copyBookEntity = bookService.searchCopyBookAvailable(isbn);
            if (copyBookEntity == null) {
                return new ResponseEntity<>("No available copies found", HttpStatus.NOT_FOUND);
            }
            copyBookEntity.setRequestEntities(null);
            copyBookEntity.setLoanEntities(null);
            copyBookEntity.setBookEntity(null);
            return new ResponseEntity<>(copyBookEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error searching for available copy-"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-list-popular")
    public ResponseEntity<List<BookEntity>> getPopularBooks() {
        try {
            List<BookEntity> bookEntities = bookService.getListPopularCopyBook().stream().map(bookEntity -> {
                bookEntity.getCopyBookEntities().forEach(copyBookEntity -> {
                    copyBookEntity.setBookEntity(null);
                    copyBookEntity.setRequestEntities(null);
                    copyBookEntity.setLoanEntities(null);
                });
                return bookEntity;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(bookEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateBook(@RequestBody BookEntity bookEntity) {
        if (bookEntity == null || bookEntity.getIsbnLibr() == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        try {
            boolean result = bookService.updateEntity(bookEntity);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
