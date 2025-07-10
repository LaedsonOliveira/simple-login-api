package com.dev.laedson.simple_auth_api.repository;

import com.dev.laedson.simple_auth_api.entity.Account;
import com.dev.laedson.simple_auth_api.entity.BillingAddress;
import com.dev.laedson.simple_auth_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddresssRepository extends JpaRepository<BillingAddress, UUID> {
}
