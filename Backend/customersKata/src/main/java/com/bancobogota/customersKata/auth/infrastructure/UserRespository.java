package com.bancobogota.customersKata.auth.infrastructure;

import com.bancobogota.customersKata.auth.domain.User;
import com.bancobogota.customersKata.customers.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
