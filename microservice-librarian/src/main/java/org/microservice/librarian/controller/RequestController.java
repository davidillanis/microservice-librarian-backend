package org.microservice.librarian.controller;

import jakarta.validation.Valid;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.entity.RequestEntity;
import org.microservice.librarian.service.RequestService;
import org.microservice.librarian.util.dto.ResponseStatusDTO;
import org.microservice.librarian.util.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDTO> createRequest(@Valid @RequestBody RequestEntity requestEntity, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            requestService.createEntity(requestEntity);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<RequestEntity>> listBooks() {
        List<RequestEntity> requestEntities=requestService.getListEntity().stream().map(requestEntity -> {
            requestEntity.getCopyBookEntity().setBookEntity(null);
            requestEntity.getCopyBookEntity().setRequestEntities(null);
            requestEntity.getCopyBookEntity().setLoanEntities(null);
            //requestEntity.getStudentEntity().setRequestEntities(null);
            //requestEntity.getStudentEntity().setUserEntity(null);
            //requestEntity.getStudentEntity().setLoanEntities(null);
            return requestEntity;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(requestEntities, HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Integer id) {
        try {
            RequestEntity requestEntity=requestService.getEntity(id);
            //requestEntity.getStudentEntity().setRequestEntities(null);
            //requestEntity.getStudentEntity().setUserEntity(null);
            //requestEntity.getStudentEntity().setLoanEntities(null);

            requestEntity.getCopyBookEntity().setLoanEntities(null);
            requestEntity.getCopyBookEntity().setBookEntity(null);
            requestEntity.getCopyBookEntity().setRequestEntities(null);
            return new ResponseEntity<>(requestEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStatusDTO> updateBook(@RequestBody RequestEntity requestEntity, BindingResult bindingResult) {
        /*Boolean status=requestService.updateEntity(requestEntity);
        if(status){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }*/

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO
                    .responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            requestService.updateEntity(requestEntity);
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(true).message(EMessage.SUCCESSFUL)
                    .errors(null).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.INTERNAL_SERVER_ERROR)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
