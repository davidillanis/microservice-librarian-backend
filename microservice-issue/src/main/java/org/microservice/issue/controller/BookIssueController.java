package org.microservice.issue.controller;


import jakarta.validation.Valid;
import org.microservice.issue.service.BookIssueService;
import org.microservice.issue.util.dto.BookIssueDTO;
import org.microservice.issue.util.dto.ResponseStatusDTO;
import org.microservice.issue.util.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book-issue")
public class BookIssueController {
    @Autowired
    private BookIssueService bookIssueService;

    @GetMapping("/search/{issue}")
    public ResponseEntity<Map<String, List<String>>> searchBookByIssue(@PathVariable String issue){
        return new ResponseEntity<>(bookIssueService.searchBookByIndexByStream(issue), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDTO> createBook(@Valid @RequestBody BookIssueDTO book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(bindingResult.getFieldErrors().stream()
                            .map(error -> error.getField() + ": " + error.getDefaultMessage())
                            .collect(Collectors.toList()))
                    .build(), HttpStatus.BAD_REQUEST);
        }
        try {
            bookIssueService.createEntity(book);
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(true).message(EMessage.SUCCESSFUL).build(), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.ERROR_INTERNAL_SERVER)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}