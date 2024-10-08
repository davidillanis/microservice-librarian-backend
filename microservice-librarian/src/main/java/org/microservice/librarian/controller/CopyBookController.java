package org.microservice.librarian.controller;

import jakarta.validation.Valid;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.service.CopyBookService;
import org.microservice.librarian.util.dto.ResponseStatusDTO;
import org.microservice.librarian.util.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copy-book")
public class CopyBookController {

    @Autowired
    private CopyBookService copyBookService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDTO> createCopyBook(@Valid @RequestBody CopyBookEntity copyBook, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            copyBookService.createEntity(copyBook);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<CopyBookEntity>> listCopyBooks() {
        try {
            return ResponseEntity.ok(copyBookService.getListEntity());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getCopyBookById(@PathVariable String id) {
        try {
            CopyBookEntity copyBook = copyBookService.getEntity(id);
            return new ResponseEntity<>(copyBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStatusDTO> updateCopyBook(@Valid @RequestBody CopyBookEntity copyBook, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            copyBookService.updateEntity(copyBook);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStatusDTO> deleteCopyBook(@PathVariable String id) {
        try {
            copyBookService.deleteEntity(id);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.ERROR_GENERIC)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/enable/{id}")
    public ResponseEntity<ResponseStatusDTO> enableCopyBook(@PathVariable String id, @RequestParam Boolean enable) {
        //this Book entity is null
        try {
            CopyBookEntity copyBook = copyBookService.getEntity(id);
            copyBook.setCodiEjem("C003");
            copyBook.setHabiEjem(enable);
            copyBookService.updateEntity(copyBook);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.ERROR_GENERIC)
                    .errors(List.of("THIS ENDPOINT DEPRECATED", e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
