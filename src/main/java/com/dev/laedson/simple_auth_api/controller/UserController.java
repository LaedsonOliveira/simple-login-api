package com.dev.laedson.simple_auth_api.controller;

import com.dev.laedson.simple_auth_api.DTO.AccountResponseDTO;
import com.dev.laedson.simple_auth_api.DTO.CreateAccountDTO;
import com.dev.laedson.simple_auth_api.DTO.CreateUserDTO;
import com.dev.laedson.simple_auth_api.DTO.UpdateUserDTO;
import com.dev.laedson.simple_auth_api.entity.Account;
import com.dev.laedson.simple_auth_api.entity.User;
import com.dev.laedson.simple_auth_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO){
        var userId = userService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("/v1/users/" + userId)).body(null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserbyId(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUser() {
        var users = userService.listUser();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateById(@PathVariable("userId") String userId,
                                           @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateById(userId, updateUserDTO);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteById(@PathVariable("userID") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<User> createUser(@PathVariable("userId") String userId,
                                           @RequestBody CreateAccountDTO createAccountDTO){
        userService.createAccount(userId, createAccountDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> createUser(@PathVariable("userId") String userId){

        var accounts = userService.listAccounts(userId);

        return ResponseEntity.ok(accounts);
    }


}
