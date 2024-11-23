package org.microservice.gateway.controller;

import org.microservice.gateway.configuration.security.JwtUtils;
import org.microservice.gateway.configuration.security.UserDetailsServiceImpl;
import org.microservice.gateway.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.gateway.utils.dto.AuthLoginRequestDTO;
import org.microservice.gateway.utils.dto.AuthResponseDTO;
import org.microservice.gateway.utils.dto.ResponseStatusDTO;
import org.microservice.gateway.utils.other.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthLoginRequestDTO authLoginRequest) {
        try {
            AuthResponseDTO response = userDetailsService.loginUser(authLoginRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return new ResponseEntity<>(AuthResponseDTO.builder()
                    .message(e.getMessage())
                    .username(null).jwt(null).status(false)
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody AuthCreateUserRequestDTO createRoleRequest) {
        try {
            AuthResponseDTO response = userDetailsService.createUser(createRoleRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatusDTO.builder()
                    .isSuccess(false).message(EMessage.ERROR_INTERNAL_SERVER).errors(List.of(e.getMessage()))
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/valid-token")
    public ResponseEntity<ResponseStatusDTO> validateToken(@RequestBody String token) {
        try {
            System.out.println(token);
            jwtUtils.validateToken(token);
            return new ResponseEntity<>( new ResponseStatusDTO(true, EMessage.SUCCESSFUL, List.of()),  HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>( new ResponseStatusDTO(false, EMessage.ERROR_VALIDATION, List.of("this token invalid")),  HttpStatus.OK);
        }
    }
}
