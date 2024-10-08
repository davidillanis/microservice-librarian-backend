package org.microservice.librarian.controller;

import jakarta.validation.Valid;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.service.LoanService;
import org.microservice.librarian.util.dto.ResponseStatusDTO;
import org.microservice.librarian.util.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDTO> updateLoan(@Valid @RequestBody LoanEntity loanEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            loanService.createEntity(loanEntity);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<LoanEntity>> getLoanList() {
        return new ResponseEntity<>(loanService.getListEntity(), HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable Integer id) {
        try {
            LoanEntity loanEntity=loanService.getEntity(id);
            return new ResponseEntity<>(loanEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStatusDTO> updateLoanById(@Valid @RequestBody LoanEntity loanEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            loanService.updateEntity(loanEntity);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
