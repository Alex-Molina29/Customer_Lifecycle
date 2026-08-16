package com.bancobogota.customersKata.auth.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResponse {
    private Long id;
    private String username;
    private String rol;
}
