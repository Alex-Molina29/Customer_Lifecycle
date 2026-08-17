package com.bancobogota.customersKata.auth.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancobogota.customersKata.auth.domain.User;

public interface UserRespository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
