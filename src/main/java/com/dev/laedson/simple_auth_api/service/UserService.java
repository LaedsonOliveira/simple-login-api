package com.dev.laedson.simple_auth_api.service;

import com.dev.laedson.simple_auth_api.DTO.CreateUserDTO;
import com.dev.laedson.simple_auth_api.DTO.UpdateUserDTO;
import com.dev.laedson.simple_auth_api.entity.User;
import com.dev.laedson.simple_auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO){
        var entity = new User(
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password()
        );

        var userSaved = userRepository.save(entity);
        return userSaved.getId();
    }

    public Optional<User> getUserById(String userID) {
        return  userRepository.findById(UUID.fromString(userID));
    }

    public List<User> listUser() {
        return userRepository.findAll();
    }

    public void updateById(String userId,
                           UpdateUserDTO updateUserDTO) {
        var userEntity = userRepository.findById(UUID.fromString(userId));

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUsername(updateUserDTO.username());
            }
            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }

    }

    public void deleteById(String userId) {

        var userExists = userRepository.existsById(UUID.fromString(userId));

        if (userExists){
            userRepository.deleteById(UUID.fromString(userId));
        }
    }

}
