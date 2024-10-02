package org.microservice.gateway.configuration.security;

import org.microservice.gateway.client.UserRoleClient;
import org.microservice.gateway.utils.dto.UserEntityDTO;
import org.microservice.gateway.utils.other.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRoleClient userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntityDTO userEntity= userRepository.getUserRoleByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("this "+username+" does not exist"));

        List<SimpleGrantedAuthority> authorityList= userEntity.getRoles().stream()
                .map(role->new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))).toList();

        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, true, authorityList);
    }
}
