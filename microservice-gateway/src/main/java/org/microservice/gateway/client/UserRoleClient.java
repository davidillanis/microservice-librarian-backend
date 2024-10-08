package org.microservice.gateway.client;

import org.microservice.gateway.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.gateway.utils.dto.ResponseStatusDTO;
import org.microservice.gateway.utils.dto.UserEntityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "microservice-users", url = "http://localhost:8083/users/api/v1/user-role")
public interface UserRoleClient {
    @GetMapping("/byUsername/{username}")
    Optional<UserEntityDTO> getUserRoleByUsername(@PathVariable String username);

    @PostMapping("/create")
    ResponseStatusDTO createUserRole(@RequestBody AuthCreateUserRequestDTO user) throws Exception;
}
