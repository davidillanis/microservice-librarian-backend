package org.microservice.gateway.controller;

import org.microservice.gateway.configuration.security.UserDetailsServiceImpl;
import org.microservice.gateway.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.gateway.utils.dto.AuthLoginRequestDTO;
import org.microservice.gateway.utils.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthLoginRequestDTO authLoginRequest) {
        try {
            AuthResponseDTO response = userDetailsService.loginUser(authLoginRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return new ResponseEntity<>(AuthResponseDTO.builder()
                    .message("This username or password is incorrect")
                    .username(null).jwt(null).status(false)
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> createUser(@RequestBody AuthCreateUserRequestDTO createRoleRequest) {
        try {
            AuthResponseDTO response = userDetailsService.createUser(createRoleRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return new ResponseEntity<>(AuthResponseDTO.builder()
                    .message("This username or dni duplicate").jwt(null).status(false)
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
