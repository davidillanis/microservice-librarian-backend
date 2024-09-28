package org.microservice.librarian.controller;

import org.microservice.librarian.model.entity.RequestEntity;
import org.microservice.librarian.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Boolean> createRequest(@RequestBody RequestEntity requestEntity) {
        Boolean status=requestService.createEntity(requestEntity);
        if(status){
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
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
        RequestEntity requestEntity=requestService.getEntity(id);
        //requestEntity.getStudentEntity().setRequestEntities(null);
        //requestEntity.getStudentEntity().setUserEntity(null);
        //requestEntity.getStudentEntity().setLoanEntities(null);

        requestEntity.getCopyBookEntity().setLoanEntities(null);
        requestEntity.getCopyBookEntity().setBookEntity(null);
        requestEntity.getCopyBookEntity().setRequestEntities(null);
        return new ResponseEntity<>(requestEntity, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateBook(@RequestBody RequestEntity requestEntity) {
        Boolean status=requestService.updateEntity(requestEntity);
        if(status){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
