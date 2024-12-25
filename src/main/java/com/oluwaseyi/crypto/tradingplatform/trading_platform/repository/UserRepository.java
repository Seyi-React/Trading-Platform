package com.oluwaseyi.crypto.tradingplatform.trading_platform.repository;

import com.oluwaseyi.crypto.tradingplatform.trading_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}
