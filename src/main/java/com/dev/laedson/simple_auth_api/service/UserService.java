package com.dev.laedson.simple_auth_api.service;

import com.dev.laedson.simple_auth_api.DTO.AccountResponseDTO;
import com.dev.laedson.simple_auth_api.DTO.CreateAccountDTO;
import com.dev.laedson.simple_auth_api.DTO.CreateUserDTO;
import com.dev.laedson.simple_auth_api.DTO.UpdateUserDTO;
import com.dev.laedson.simple_auth_api.entity.Account;
import com.dev.laedson.simple_auth_api.entity.BillingAddress;
import com.dev.laedson.simple_auth_api.entity.User;
import com.dev.laedson.simple_auth_api.repository.AccountRepository;
import com.dev.laedson.simple_auth_api.repository.BillingAddresssRepository;
import com.dev.laedson.simple_auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddresssRepository billingAddresssRepository;


    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddresssRepository billingAddresssRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddresssRepository = billingAddresssRepository;
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

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {

        var verifyUser = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                createAccountDTO.description(),
                verifyUser,
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDTO.street(),
                createAccountDTO.number()
        );

        billingAddresssRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccountList()
                .stream()
                .map(account ->  new AccountResponseDTO(account.getAccountId().toString(), account.getDescription()))
                .toList();
    }
}
