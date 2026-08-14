package com.bancobogota.customersKata.customers.application.dto.response;

import com.bancobogota.customersKata.customers.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerList {
    private Integer totalPages;
    private Long totalElements;
    private List<CustomerEntryList> customers;
}