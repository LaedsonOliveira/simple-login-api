package com.dev.laedson.simple_auth_api.service;

import com.dev.laedson.simple_auth_api.DTO.AccountResponseDTO;
import com.dev.laedson.simple_auth_api.DTO.AccountStockResponseDTO;
import com.dev.laedson.simple_auth_api.DTO.AssociateAccountStockDTO;
import com.dev.laedson.simple_auth_api.client.BrapiClient;
import com.dev.laedson.simple_auth_api.entity.AccountStockId;
import com.dev.laedson.simple_auth_api.entity.AccountsStock;
import com.dev.laedson.simple_auth_api.repository.AccountRepository;
import com.dev.laedson.simple_auth_api.repository.AccountStockRepository;
import com.dev.laedson.simple_auth_api.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private BrapiClient brapiClient;

    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, BrapiClient brapiClient, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.brapiClient = brapiClient;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateAccountStock(String accountId, AssociateAccountStockDTO associateStockDTO) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateStockDTO.stockId())
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var entity = new AccountsStock(
                id,
                account,
                stock,
                associateStockDTO.quantity()
        );

        accountStockRepository.save(entity);

    }

    public List<AccountStockResponseDTO> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountsStocks()
                .stream()
                .map(as -> new AccountStockResponseDTO(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotal(as.getQuantity(), as.getStock().getStockId())
                ))
                .toList();

    }

    private double getTotal(Integer quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);

        double price = response.results().get(0).regularMarketPrice();

        return quantity * price;
    }
}
