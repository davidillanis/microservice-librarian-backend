package org.microservice.issue.controller;


import org.microservice.issue.service.BookIssueService;
import org.microservice.issue.util.dto.BookIssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createBook(@RequestBody BookIssueDTO book){
        try {
            return new ResponseEntity<>(bookIssueService.createEntity(book), HttpStatus.CREATED);
        }catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}