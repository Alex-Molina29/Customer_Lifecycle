package com.bancobogota.customersKata.customers.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bancobogota.customersKata.customers.domain.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByDocumentNumber(String documentNumber);
    Customer findByEmail(String email);
}
