package org.microservice.librarian.controller;

import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> updateLoan(@RequestBody LoanEntity loanEntity) {
        Boolean status=loanService.createEntity(loanEntity);
        if(status){
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<LoanEntity>> getLoanList() {
        return new ResponseEntity<>(loanService.getListEntity(), HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Object> getLoanById(@PathVariable Integer id) {
        LoanEntity loanEntity=loanService.getEntity(id);
        return new ResponseEntity<>(loanEntity, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateLoanById(@RequestBody LoanEntity loanEntity) {
        Boolean status=loanService.updateEntity(loanEntity);
        if(status){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
