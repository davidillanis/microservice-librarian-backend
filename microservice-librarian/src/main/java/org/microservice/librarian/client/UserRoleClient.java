package org.microservice.librarian.client;

import org.microservice.librarian.util.dto.UserEntityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-users", url = "http://localhost:8083/users/api/v1/user-role")
public interface UserRoleClient {
    @GetMapping("/byId/{id}")
    UserEntityDTO getUserRoleByUsername(@PathVariable Integer id);
}
