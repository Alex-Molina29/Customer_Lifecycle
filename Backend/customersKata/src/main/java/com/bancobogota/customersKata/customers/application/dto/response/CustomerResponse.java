package com.bancobogota.customersKata.customers.application.dto.response;

import com.bancobogota.customersKata.customers.domain.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private long id;
    private String documentType;
    private String documentNumber;
    private String name;
    private String email;
    private String phoneNumber;

    public static CustomerResponse of(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .documentType(customer.getDocumentType())
                .documentNumber(customer.getDocumentNumber())
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
