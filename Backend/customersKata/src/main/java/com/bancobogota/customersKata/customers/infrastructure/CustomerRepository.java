package com.bancobogota.customersKata.customers.infrastructure;

import com.bancobogota.customersKata.customers.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository  extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByDocumentNumber(String documentNumber);
    Customer findByEmail(String email);
}
