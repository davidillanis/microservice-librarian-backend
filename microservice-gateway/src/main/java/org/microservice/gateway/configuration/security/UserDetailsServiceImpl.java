package org.microservice.gateway.configuration.security;

import org.microservice.gateway.client.UserRoleClient;
import org.microservice.gateway.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.gateway.utils.dto.AuthLoginRequestDTO;
import org.microservice.gateway.utils.dto.AuthResponseDTO;
import org.microservice.gateway.utils.dto.UserEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRoleClient userRoleClient;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntityDTO userEntity= userRoleClient.getUserRoleByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("this "+username+" does not exist"));

        List<SimpleGrantedAuthority> authorityList= userEntity.getRoles().stream()
                .map(role->new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))).toList();

        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, true, authorityList);
    }

    public AuthResponseDTO createUser(AuthCreateUserRequestDTO createRoleRequest) throws Exception{
        Set<String> rolesRequest = createRoleRequest.roleList().stream().map(role->role.name()).collect(Collectors.toSet());

        if(rolesRequest.isEmpty()){
            throw new UsernameNotFoundException("the role is empty or not exist");
        }
        userRoleClient.createUserRole(createRoleRequest);

        System.out.println(rolesRequest);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
                //createRoleRequest.roleList().stream().map(role->new SimpleGrantedAuthority("ROLE_".concat(role.name()))).toList();
        createRoleRequest.roleList().stream().forEach(role->authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.name()))));
        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(createRoleRequest, null, authorities);
        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponseDTO(createRoleRequest.username(), "User created successfully", accessToken, true);
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponseDTO(username, "User loged succesfully", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
