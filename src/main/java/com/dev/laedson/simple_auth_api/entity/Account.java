package com.dev.laedson.simple_auth_api.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_account")
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "account")
    private List<AccountsStock> accountsStocks;

    public Account() {
    }

    public Account(UUID accountId, String description, User user, BillingAddress billingAddress, List<AccountsStock> accountsStocks) {
        this.accountId = accountId;
        this.description = description;
        this.user = user;
        this.billingAddress = billingAddress;
        this.accountsStocks = accountsStocks;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<AccountsStock> getAccountsStocks() {
        return accountsStocks;
    }

    public void setAccountsStocks(List<AccountsStock> accountsStocks) {
        this.accountsStocks = accountsStocks;
    }
}
