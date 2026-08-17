package com.bancobogota.customersKata.customers.application.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerList {
    private Integer totalPages;
    private Long totalElements;
    private List<CustomerEntryList> customers;
}