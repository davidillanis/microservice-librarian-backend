package org.microservice.librarian.client;

import org.microservice.librarian.util.dto.StudentEntityDTO;
import org.microservice.librarian.util.dto.UserEntityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "microservice-users", url = "http://localhost:8083/users/api/v1/user-role")
public interface UserRoleClient {
    @GetMapping("/byId/{id}")
    Optional<UserEntityDTO> getUserRoleByUsername(@PathVariable Integer id);

    @GetMapping("/student/byUsername/{username}")
    Optional<StudentEntityDTO> getStudentByUsername(@PathVariable String username);
}
