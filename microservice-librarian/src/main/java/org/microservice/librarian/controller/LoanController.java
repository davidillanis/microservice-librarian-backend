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
@RequestMapping("/api/library/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> updateLoan(@RequestBody LoanEntity loanEntity) {
        if(loanEntity!=null){
            return new ResponseEntity<>(loanService.createEntity(loanEntity), HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/list")
    public ResponseEntity<List<LoanEntity>> getLoanList() {
        List<LoanEntity> loanEntities=loanService.getListEntity().stream().map(loanEntity -> {
            //loanEntity.getLibrarianEntity().setLoanEntities(null);
            //loanEntity.getLibrarianEntity().setUserEntity(null);

//            loanEntity.getCopyBookEntity().setLoanEntities(null);
//            loanEntity.getCopyBookEntity().setRequestEntities(null);
//            loanEntity.getCopyBookEntity().setBookEntity(null);

            //loanEntity.getStudentEntity().setUserEntity(null);
            //loanEntity.getStudentEntity().setLoanEntities(null);
            //loanEntity.getStudentEntity().setRequestEntities(null);
            return loanEntity;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(loanEntities, HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Object> getLoanById(@PathVariable Integer id) {
        LoanEntity loanEntity=loanService.getEntity(id);
        //loanEntity.getLibrarianEntity().setLoanEntities(null);
        //loanEntity.getLibrarianEntity().setUserEntity(null);

//        loanEntity.getCopyBookEntity().setLoanEntities(null);
//        loanEntity.getCopyBookEntity().setRequestEntities(null);
//        loanEntity.getCopyBookEntity().setBookEntity(null);

        //loanEntity.getStudentEntity().setUserEntity(null);
        //loanEntity.getStudentEntity().setLoanEntities(null);
        //loanEntity.getStudentEntity().setRequestEntities(null);
        return new ResponseEntity<>(loanEntity, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateLoanById(@RequestBody LoanEntity loanEntity) {
        if(loanEntity!=null){
            return new ResponseEntity<>(loanService.updateEntity(loanEntity), HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
