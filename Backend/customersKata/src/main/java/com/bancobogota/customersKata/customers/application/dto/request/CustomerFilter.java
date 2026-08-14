package com.bancobogota.customersKata.customers.application.dto.request;

import lombok.Data;

@Data
public class CustomerFilter {
    private String documentType;
    private String documentNumber;
    private String name;
    private String email;
    private String phoneNumber;
}