package com.dev.laedson.simple_auth_api.controller;


import com.dev.laedson.simple_auth_api.DTO.AccountStockResponseDTO;
import com.dev.laedson.simple_auth_api.DTO.AssociateAccountStockDTO;
import com.dev.laedson.simple_auth_api.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountCotroller {

    private AccountService accountService;

    public AccountCotroller(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                        @RequestBody AssociateAccountStockDTO associateStockDTO){

        accountService.associateAccountStock(accountId, associateStockDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> associateStock(@PathVariable("accountId") String accountId,
                                                                        @RequestBody AccountStockResponseDTO accountStockResponseDTO){

        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }


}
