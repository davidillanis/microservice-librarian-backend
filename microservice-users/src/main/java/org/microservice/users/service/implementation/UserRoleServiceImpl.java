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
import org.microservice.users.utils.dto.AuthUpdateUserRequestDTO;
import org.microservice.users.utils.exception.DuplicateDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getListUserEntity() {
        return userRepository.findAll().stream().map(userEntity -> {
            userEntity.setPassword(null);
            if(userEntity.getStudentEntity()!=null) {
                userEntity.getStudentEntity().setUserEntity(null);
            }
            if(userEntity.getLibrarianEntity()!=null){
                userEntity.getLibrarianEntity().setUserEntity(null);
            }
            return userEntity;
        }).collect(Collectors.toList());
    }

    @Override
    public UserEntity getUserEntityById(Integer id) {

        UserEntity userEntity=userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("this user not exists"));
        userEntity.setPassword(null);
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
    public StudentEntity getStudentByUsername(String username) {
        StudentEntity studentEntity=studentRepository.findStudentEntityByUserEntityUsername(username)
                .orElseThrow(()->new EntityNotFoundException("this user not exists"));
        studentEntity.getUserEntity().setStudentEntity(null);
        studentEntity.getUserEntity().setPassword(null);
        return studentEntity;
    }

    @Override
    public StudentEntity getStudentById(Integer id) {
        StudentEntity student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.getUserEntity().setStudentEntity(null);
            student.getUserEntity().setPassword(null);
            return student;
        }
        return null;
    }

    @Override
    public LibrarianEntity getLibrarianById(Integer id) {
        LibrarianEntity librarian = librarianRepository.findById(id).orElse(null);
        if (librarian != null) {
            librarian.getUserEntity().setLibrarianEntity(null);
            librarian.getUserEntity().setPassword(null);
            return librarian;
        }
        return null;
    }

    @Override
    public void createEntity(AuthCreateUserRequestDTO obj) throws Exception {
        if(userRepository.existsByDNIUsua(obj.usuaDNI())){
            throw new DuplicateDataException(obj.usuaDNI());
        }
        if(userRepository.existsByUsername(obj.username())){
            throw new DuplicateDataException(obj.username());
        }

        userRepository.save(UserEntity.builder()
                .idUsua(0)
                .username(obj.username())
                .password(passwordEncoder.encode(obj.password()))
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
                case STUDENT -> studentRepository.save(new StudentEntity(0, LocalDate.now(), "1A", "PRIMARIA", userEntity));
            }
        });
    }

    @Override
    public Boolean updateEntity(AuthUpdateUserRequestDTO obj) {
        UserEntity userEntity=userRepository.findUserEntityByUsername(obj.username())
                .orElseThrow(()->new EntityNotFoundException("this "+obj.username()+" user not exists"));
        if(!obj.usuaDNI().equals(userEntity.getDNIUsua()) && userRepository.existsByDNIUsua(obj.usuaDNI())){
            throw new DuplicateDataException(obj.usuaDNI());
        }

        if(userEntity!=null){
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
        UserEntity user =userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("this user not exists"));
        if(user !=null){
            user.setIsEnabled(enabled);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el username: " + username));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
        //user.setPassword(passwordEncoder.encode(newPassword));
        //userRepository.save(user);
        int rowsUpdated = userRepository.updatePasswordByUsername(username, passwordEncoder.encode(newPassword));
        if (rowsUpdated == 0) {
            throw new IllegalStateException("No se pudo actualizar la contraseña.");
        }
    }

    @Override
    public void updatePassword(String username, String Password) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el username: " + username));
        user.setPassword(passwordEncoder.encode(Password));
        userRepository.save(user);
    }

    @Override
    public Boolean deleteEntity(Integer id) {
        UserEntity user =userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("this user not exists"));
        if(user !=null){
            user.setRoles(null);
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
