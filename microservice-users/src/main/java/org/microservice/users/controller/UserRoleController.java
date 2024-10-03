package org.microservice.users.controller;

import org.microservice.users.model.entity.LibrarianEntity;
import org.microservice.users.model.entity.StudentEntity;
import org.microservice.users.model.entity.UserEntity;
import org.microservice.users.service.UserRoleService;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserEntity> getUserRoleById(@PathVariable Integer id) {
        UserEntity userEntity=userRoleService.getUserEntityById(id);
        userEntity.setPassword(null);
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/byUsername/{username}")//this enpoint use in microservice gateway
    public ResponseEntity<UserEntity> getUserRoleByUsername(@PathVariable String username) {
        UserEntity userEntity=userRoleService.getUserEntityByUsername(username);
        //userEntity.setPassword(null);
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/student/byId/{id}")
    public ResponseEntity<StudentEntity> getStudent(@PathVariable Integer id) {
        StudentEntity student=userRoleService.getStudentById(id);
        student.getUserEntity().setPassword(null);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/librarian/byId/{id}")
    public ResponseEntity<LibrarianEntity> getLibrarian(@PathVariable Integer id) {
        LibrarianEntity librarian=userRoleService.getLibrarianById(id);
        librarian.getUserEntity().setPassword(null);
        return ResponseEntity.ok(librarian);
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody AuthCreateUserRequestDTO authUser) {
        Boolean status = userRoleService.createEntity(authUser);
        if(status){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody AuthCreateUserRequestDTO authUser){
        return new ResponseEntity<>(userRoleService.updateEntity(authUser), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/status/{id}/{enabled}")
    public ResponseEntity<Boolean> updateStatus(@PathVariable Integer id, @PathVariable Boolean enabled){
        return new ResponseEntity<>(userRoleService.updateEnabledAccount(id,enabled), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id){
        return new ResponseEntity<>(userRoleService.deleteEntity(id), HttpStatus.OK);
    }
}
