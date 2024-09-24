package org.microservice.users.controller;

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
    public ResponseEntity<?> getUserRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(userRoleService.getUserEntityById(id));
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<?> getUserRoleByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userRoleService.getUserEntityByUsername(username));
    }

    @GetMapping("/student/byId/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Integer id) {
        System.out.println(userRoleService.getStudentById(id).toString().concat(id.toString()));
        return ResponseEntity.ok(userRoleService.getStudentById(id));
    }

    @GetMapping("/librarian/byId/{id}")
    public ResponseEntity<?> getLibrarian(@PathVariable Integer id) {
        return ResponseEntity.ok(userRoleService.getLibrarianById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody AuthCreateUserRequestDTO authUser){
        Map<String, Object> map=new HashMap<>();
        try {
            userRoleService.createEntity(authUser);
            map.put("status", true);
            map.put("value", "OK");
        }catch (Exception e){
            map.put("status", false);
            map.put("value", "This Username or dni already exist");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody AuthCreateUserRequestDTO authUser){
        return new ResponseEntity<>(userRoleService.updateEntity(authUser), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/status/{id}/{enabled}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @PathVariable Boolean enabled){
        return new ResponseEntity<>(userRoleService.updateEnabledAccount(id,enabled), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        return new ResponseEntity<>(userRoleService.deleteEntity(id), HttpStatus.OK);
    }
}
