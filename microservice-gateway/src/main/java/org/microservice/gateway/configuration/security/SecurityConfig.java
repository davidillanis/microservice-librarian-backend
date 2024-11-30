package org.microservice.gateway.configuration.security;

import org.microservice.gateway.utils.other.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(cors->cors.configure(http))
                .csrf(csrf->csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize->authorize

                        //MICROSERVICE USERS
                        .requestMatchers("/users/api/v1/user-role/librarian/byId/**").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/student/byId/**").hasAnyRole(ERole.LIBRARIAN.name(), ERole.STUDENT.name())
                        .requestMatchers("/users/api/v1/user-role/list").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/create").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/update").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/update-password/**").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/byId/**").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/byUsername/**").hasAnyRole(ERole.LIBRARIAN.name(), ERole.STUDENT.name())
                        .requestMatchers("/users/api/v1/user-role/status/**").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/users/api/v1/user-role/delete/byId/**").hasRole(ERole.LIBRARIAN.name())//ROLE ADMIN

                        //MICROSERVICE LIBRARY
                        .requestMatchers("/librarian/api/v1/book/list").permitAll()
                        .requestMatchers("/librarian/api/v1/book/create").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/book/create/book-issue").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/book/searchByISBN/**").permitAll()
                        .requestMatchers("/librarian/api/v1/book/search-copy-available/**").permitAll()
                        .requestMatchers("/librarian/api/v1/book/get-list-popular").permitAll()
                        .requestMatchers("/librarian/api/v1/book/update").hasRole(ERole.LIBRARIAN.name())

                        .requestMatchers("/librarian/api/v1/copy-book/list").hasAnyRole(ERole.STUDENT.name(), ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/copy-book/byId/**").hasAnyRole(ERole.STUDENT.name(), ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/copy-book/create").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/copy-book/update").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/copy-book/delete/**").hasRole(ERole.LIBRARIAN.name())//ROLE ADMIN
                        .requestMatchers("/librarian/api/v1/copy-book/enable/**").hasRole(ERole.LIBRARIAN.name())//DEPRECATED

                        .requestMatchers("/librarian/api/v1/loan/byId/**").hasAnyRole(ERole.LIBRARIAN.name(), ERole.STUDENT.name())
                        .requestMatchers("/librarian/api/v1/loan/create").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/loan/list").hasAnyRole(ERole.LIBRARIAN.name(), ERole.STUDENT.name())
                        .requestMatchers("/librarian/api/v1/loan/list/student/byUsername/**").hasAnyRole(ERole.LIBRARIAN.name(), ERole.STUDENT.name())
                        .requestMatchers("/librarian/api/v1/loan/update").hasRole(ERole.LIBRARIAN.name())

                        .requestMatchers("/librarian/api/v1/request/create").hasRole(ERole.STUDENT.name())
                        .requestMatchers("/librarian/api/v1/request/list").hasAnyRole(ERole.STUDENT.name(), ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/request/list/student/byUsername/**").hasAnyRole(ERole.STUDENT.name(), ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/request/byId/**").hasAnyRole(ERole.STUDENT.name(), ERole.LIBRARIAN.name())
                        .requestMatchers("/librarian/api/v1/request/update").hasAnyRole(ERole.STUDENT.name(), ERole.LIBRARIAN.name())

                        //MICROSERVICE ISSUE
                        .requestMatchers("/issue/api/v1/book-issue/search/**").permitAll()
                        .requestMatchers("/issue/api/v1/book-issue/create").hasRole(ERole.LIBRARIAN.name())

                        //UTIL
                        .requestMatchers("/utils/api/v1/email/sendMessage").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/utils/api/v1/email/sendMessageFile").hasRole(ERole.LIBRARIAN.name())
                        .requestMatchers("/utils/api/v1/email/send-message-template").hasRole(ERole.LIBRARIAN.name())

                        //GATEWAY
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/valid-token").permitAll()
                        .requestMatchers("/auth/register").hasRole(ERole.LIBRARIAN.name())

                        .anyRequest().denyAll())
                .addFilterBefore(new JwtTokenValidator(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
