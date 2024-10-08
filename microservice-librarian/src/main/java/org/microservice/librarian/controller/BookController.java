package org.microservice.librarian.controller;


import jakarta.validation.Valid;
import org.microservice.librarian.model.entity.BookEntity;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.service.BookService;
import org.microservice.librarian.util.dto.ResponseStatusDTO;
import org.microservice.librarian.util.http.request.BookIssueRequest;
import org.microservice.librarian.util.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDTO> createBook(@Valid @RequestBody BookEntity bookEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            bookService.createEntity(bookEntity);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/book-issue")
    public ResponseEntity<?> createBookIssue(@Valid @RequestBody BookIssueRequest bookIssueRequest, BindingResult binding) {
        if (binding.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(binding, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            bookService.createBookIssue(bookIssueRequest);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            BookEntity bookEntity = bookService.findBookEntityByIsbnLibr(isbn);
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
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search-copy-available/{isbn}")
    public ResponseEntity<?> searchCopyBookAvailable(@PathVariable String isbn) {
        try {
            CopyBookEntity copyBookEntity = bookService.searchCopyBookAvailable(isbn);
            if (copyBookEntity == null) {
                return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                        .errors(List.of("No available copies found")).build(), HttpStatus.NOT_FOUND);
            }
            copyBookEntity.setRequestEntities(null);
            copyBookEntity.setLoanEntities(null);
            copyBookEntity.setBookEntity(null);
            return new ResponseEntity<>(copyBookEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<ResponseStatusDTO> updateBook(@Valid @RequestBody BookEntity bookEntity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            bookService.updateEntity(bookEntity);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.ERROR_GENERIC)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
