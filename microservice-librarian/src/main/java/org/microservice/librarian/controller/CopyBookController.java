package org.microservice.librarian.controller;

import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.service.CopyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/copy-book")
public class CopyBookController {

    @Autowired
    private CopyBookService copyBookService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createCopyBook(@RequestBody CopyBookEntity copyBook) {
        if (copyBook == null) {
            return ResponseEntity.badRequest().body(false);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(copyBookService.createEntity(copyBook));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<CopyBookEntity>> listCopyBooks() {
        try {
            List<CopyBookEntity> copyBooks = copyBookService.getListEntity().stream()
                    .peek(copyBook -> {
                        if (copyBook.getBookEntity() != null) {
                            copyBook.getBookEntity().setCopyBookEntities(null);
                        }
                        clearEntityRelations(copyBook);
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(copyBooks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getCopyBookById(@PathVariable String id) {
        try {
            CopyBookEntity copyBook = copyBookService.getEntity(id);
            if (copyBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Copy Book not found");
            }
            if (copyBook.getBookEntity() != null) {
                copyBook.getBookEntity().setCopyBookEntities(null);
            }
            clearEntityRelations(copyBook);
            return ResponseEntity.ok(copyBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving Copy Book");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateCopyBook(@RequestBody CopyBookEntity copyBook) {
        if (copyBook == null || copyBook.getCodiEjem() == null) {
            return ResponseEntity.badRequest().body(false);
        }
        try {
            return ResponseEntity.ok(copyBookService.updateEntity(copyBook));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCopyBook(@PathVariable String id) {
        try {
            CopyBookEntity copyBook = copyBookService.getEntity(id);
            if (copyBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
            return ResponseEntity.ok(copyBookService.deleteEntity(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @DeleteMapping("/enable/{id}")
    public ResponseEntity<Boolean> enableCopyBook(@PathVariable String id, @RequestParam Boolean enable) {
        try {
            CopyBookEntity copyBook = copyBookService.getEntity(id);
            if (copyBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
            copyBook.setHabiEjem(enable);
            return ResponseEntity.ok(copyBookService.updateEntity(copyBook));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    // Helper method to clean entity relations
    private void clearEntityRelations(CopyBookEntity copyBook) {
        copyBook.setLoanEntities(copyBook.getLoanEntities().stream()
                .peek(loan -> loan.setCopyBookEntity(null))
                .collect(Collectors.toList()));
        copyBook.setRequestEntities(copyBook.getRequestEntities().stream()
                .peek(request -> request.setCopyBookEntity(null))
                .collect(Collectors.toList()));
    }
}
