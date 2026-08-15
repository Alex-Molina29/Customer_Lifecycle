package com.bancobogota.customersKata.customers.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_type", unique = false, nullable = true)
    private String documentType;

    @Column(name = "document_number", unique = true, nullable = true)
    private String documentNumber;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = true)
    private String phoneNumber;
}
