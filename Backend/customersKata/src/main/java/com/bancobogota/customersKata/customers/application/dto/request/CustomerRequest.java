package com.bancobogota.customersKata.customers.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "El tipo de documento es obligatorio")
    private String documentType;

    @NotBlank(message = "El número de documento es obligatorio")
    private String documentNumber;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phoneNumber;
}
