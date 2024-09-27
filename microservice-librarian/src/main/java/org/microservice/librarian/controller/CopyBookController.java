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
@RequestMapping("/api/library/copy-book")
public class CopyBookController {
    @Autowired
    private CopyBookService copyBookService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createCopyBook(@RequestBody CopyBookEntity copyBook){
        System.out.println(copyBook);
        try {
            return new ResponseEntity<>(copyBookService.createEntity(copyBook), HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<CopyBookEntity>> listCopyBook(){
        List<CopyBookEntity> copyBookEntities=copyBookService.getListEntity().stream().map(copyBook ->{
                copyBook.getBookEntity().setCopyBookEntities(null);

                copyBook.setLoanEntities(copyBook.getLoanEntities().stream().map(loanEntity -> {
                    loanEntity.setCopyBookEntity(null);
                    //loanEntity.setLibrarianEntity(null);
                    //loanEntity.setStudentEntity(null);
                    return loanEntity;
                }).collect(Collectors.toList()));

                copyBook.setRequestEntities(copyBook.getRequestEntities().stream().map(requestEntity -> {
                    requestEntity.setCopyBookEntity(null);
                    //requestEntity.setStudentEntity(null);
                    return requestEntity;
                }).collect(Collectors.toList()));
                return copyBook;
            }).collect(Collectors.toList());
        return new ResponseEntity<>(copyBookEntities, HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<CopyBookEntity> getCopyBookById(@PathVariable("id") String id){
        CopyBookEntity copyBook=copyBookService.getEntity(id);
        copyBook.getBookEntity().setCopyBookEntities(null);
        copyBook.setRequestEntities(copyBook.getRequestEntities().stream().map(requestEntity -> {
            requestEntity.setCopyBookEntity(null);
            //requestEntity.setStudentEntity(null);
            return requestEntity;
        }).toList());
        copyBook.setLoanEntities(copyBook.getLoanEntities().stream().map(loanEntity -> {
            loanEntity.setCopyBookEntity(null);
            //loanEntity.setLibrarianEntity(null);
            //loanEntity.setStudentEntity(null);
            return loanEntity;
        }).collect(Collectors.toList()));
        return new ResponseEntity<>(copyBook, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateCopyBook(@RequestBody CopyBookEntity copyBook){
        try {
            return new ResponseEntity<>(copyBookService.updateEntity(copyBook), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCopyBook(@PathVariable("id") String id){
        CopyBookEntity copyBook = copyBookService.getEntity(id);
        if(copyBook != null){
            return new ResponseEntity<>(copyBookService.deleteEntity(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/enable/{id}")
    public ResponseEntity<Boolean> enableCopyBook(@PathVariable("id") String id, @RequestParam Boolean enable){
        CopyBookEntity copyBook = copyBookService.getEntity(id);
        if(copyBook != null){
            copyBook.setHabiEjem(enable);
            return new ResponseEntity<>(copyBookService.updateEntity(copyBook), HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }


}
