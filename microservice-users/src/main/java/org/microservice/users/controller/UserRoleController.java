package org.microservice.users.controller;

import jakarta.validation.Valid;
import org.microservice.users.model.entity.LibrarianEntity;
import org.microservice.users.model.entity.StudentEntity;
import org.microservice.users.model.entity.UserEntity;
import org.microservice.users.service.UserRoleService;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.users.utils.dto.AuthUpdateUserRequestDTO;
import org.microservice.users.utils.dto.ResponseStatusDTO;
import org.microservice.users.utils.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        try{
            return ResponseEntity.ok(userRoleService.getListUserEntity());
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false)
                    .message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getUserRoleById(@PathVariable Integer id) {
        try{
            UserEntity userEntity=userRoleService.getUserEntityById(id);
            userEntity.setPassword(null);
            return ResponseEntity.ok(userEntity);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false)
                    .message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byUsername/{username}")//this enpoint use in microservice gateway
    public ResponseEntity<?> getUserRoleByUsername(@PathVariable String username) {
        try {
            UserEntity userEntity=userRoleService.getUserEntityByUsername(username);
            //userEntity.setPassword(null);
            return ResponseEntity.ok(userEntity);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false)
                    .message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/student/byId/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Integer id) {
        try{
            StudentEntity student=userRoleService.getStudentById(id);
            student.getUserEntity().setPassword(null);
            return ResponseEntity.ok(student);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/student/byUsername/{username}")
    public ResponseEntity<?> getStudentByUsername(@PathVariable String username) {
        try{
            StudentEntity student=userRoleService.getStudentByUsername(username);
            student.getUserEntity().setPassword(null);
            return ResponseEntity.ok(student);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/librarian/byId/{id}")
    public ResponseEntity<?> getLibrarian(@PathVariable Integer id) {
        try {
            LibrarianEntity librarian=userRoleService.getLibrarianById(id);
            librarian.getUserEntity().setPassword(null);
            return ResponseEntity.ok(librarian);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDTO> createUser(@Valid @RequestBody AuthCreateUserRequestDTO authUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ResponseStatusDTO.responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try {
            userRoleService.createEntity(authUser);
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(true).message(EMessage.SUCCESSFUL).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.ERROR_INTERNAL_SERVER).errors(List.of(e.getMessage()))
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStatusDTO> updateUser(@Valid @RequestBody AuthUpdateUserRequestDTO authUser, BindingResult bindingResult){
        if (bindingResult.hasErrors() ) {
            return new ResponseEntity<>(ResponseStatusDTO.responseStatusValid(bindingResult, EMessage.ERROR_VALIDATION), HttpStatus.BAD_REQUEST);
        }
        try{
            userRoleService.updateEntity(authUser);
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(true).message(EMessage.SUCCESSFUL).build(), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND).errors(List.of(e.getMessage()))
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/status/{id}/{enabled}")
    public ResponseEntity<ResponseStatusDTO> updateStatus(@PathVariable Integer id, @PathVariable Boolean enabled){
        //return new ResponseEntity<>(userRoleService.updateEnabledAccount(id,enabled), HttpStatus.ACCEPTED);
        try {
            userRoleService.updateEnabledAccount(id,enabled);
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(true).message(EMessage.SUCCESSFUL).build(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<ResponseStatusDTO> deleteUser(@PathVariable Integer id){
        try {
            userRoleService.deleteEntity(id);
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(true).message(EMessage.SUCCESSFUL).build(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder().isSuccess(false).message(EMessage.RESOURCE_NOT_FOUND)
                    .errors(List.of(e.getMessage())).build(), HttpStatus.NOT_FOUND);
        }
    }
}
