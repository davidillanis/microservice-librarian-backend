package org.microservice.users.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.microservice.users.model.entity.LibrarianEntity;
import org.microservice.users.model.entity.RoleEntity;
import org.microservice.users.model.entity.StudentEntity;
import org.microservice.users.model.entity.UserEntity;
import org.microservice.users.model.repository.LibrarianRepository;
import org.microservice.users.model.repository.RoleRepository;
import org.microservice.users.model.repository.StudentRepository;
import org.microservice.users.model.repository.UserRepository;
import org.microservice.users.service.UserRoleService;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity getUserEntityById(Integer id) {

        UserEntity userEntity=userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("this user not exists"));
        Optional.ofNullable(userEntity.getStudentEntity()).ifPresent(student -> student.setUserEntity(null));
        Optional.ofNullable(userEntity.getLibrarianEntity()).ifPresent(librarian -> librarian.setUserEntity(null));

        return userEntity;
    }

    @Override
    public UserEntity getUserEntityByUsername(String username) {
        UserEntity userEntity=userRepository.findUserEntityByUsername(username).orElseThrow(()->new EntityNotFoundException("this user not exists"));
        Optional.ofNullable(userEntity.getStudentEntity()).ifPresent(student -> student.setUserEntity(null));
        Optional.ofNullable(userEntity.getLibrarianEntity()).ifPresent(librarian -> librarian.setUserEntity(null));
        return userEntity;
    }

    @Override
    public StudentEntity getStudentById(Integer id) {
        StudentEntity student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.getUserEntity().setStudentEntity(null);
            return student;
        }
        return null;
    }

    @Override
    public LibrarianEntity getLibrarianById(Integer id) {
        LibrarianEntity librarian = librarianRepository.findById(id).orElse(null);
        if (librarian != null) {
            librarian.getUserEntity().setLibrarianEntity(null);
            return librarian;
        }
        return null;
    }

    @Override
    public Boolean createEntity(AuthCreateUserRequestDTO obj) {
        //AuthResponseDTO authResponseDTO=userDetailService.createUser(obj);
        try {
            userRepository.save(UserEntity.builder()
                    .idUsua(0)
                    .username(obj.username())
                    .password(obj.password())
                    .isEnabled(true)
                    .teleUsua(obj.usuaTele())
                    .DNIUsua(obj.usuaDNI())
                    .nombUsua(obj.usuaNomb())
                    .apelPaternoUsua(obj.usuaApelPaterno())
                    .apelMaternoUsua(obj.usuaApelMaterno())
                    //.roles(obj.roleList().stream().map(role->roleRepository.findRoleEntityByRole(role).orElse(null)).collect(Collectors.toSet()))
                    .roles(obj.roleList().stream().map(role-> roleRepository.findRoleEntityByRole(role)
                            .orElse(new RoleEntity(0, role))).collect(Collectors.toSet()))
                    .build());

            UserEntity userEntity=userRepository.findUserEntityByUsername(obj.username()).orElse(null);
            userEntity.getRoles().stream().forEach(roleEntity-> {
                switch (roleEntity.getRole()){
                    case LIBRARIAN -> librarianRepository.save(new LibrarianEntity(0, LocalDate.now(), userEntity));
                    case STUDENT -> studentRepository.save(new StudentEntity(0, LocalDate.now(), userEntity));
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateEntity(AuthCreateUserRequestDTO obj) {
        UserEntity userEntity=userRepository.findUserEntityByUsername(obj.username()).orElse(null);
        if(userEntity!=null){
            userEntity.setPassword(obj.password());
            userEntity.setNombUsua(obj.usuaNomb());
            userEntity.setApelMaternoUsua(obj.usuaApelMaterno());
            userEntity.setApelPaternoUsua(obj.usuaApelPaterno());
            userEntity.setDNIUsua(obj.usuaDNI());
            userEntity.setTeleUsua(obj.usuaTele());
            //userEntity.setRoles(obj.roleList().stream().map(role->roleRepository.findRoleEntityByRole(role).orElse(null)).collect(Collectors.toSet()));
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateEnabledAccount(Integer id, Boolean enabled) {
        UserEntity userEntity=userRepository.findById(id).orElse(null);
        if(userEntity!=null){
            userEntity.setIsEnabled(enabled);
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteEntity(Integer id) {
        UserEntity userEntity=userRepository.findById(id).orElse(null);
        if(userEntity!=null){
            userEntity.setRoles(null);
            userRepository.delete(userEntity);
            return true;
        }
        return false;
    }
}
