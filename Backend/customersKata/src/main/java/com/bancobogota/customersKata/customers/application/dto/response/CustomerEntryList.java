package com.bancobogota.customersKata.customers.application.dto.response;

import com.bancobogota.customersKata.customers.domain.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerEntryList {
    private Long id;
    private String documentNumber;
    private String name;

    public static CustomerEntryList of(Customer customer) {
        return CustomerEntryList.builder()
                .id(customer.getId())
                .documentNumber(customer.getDocumentNumber())
                .name(customer.getName())
                .build();
    }

    
}
