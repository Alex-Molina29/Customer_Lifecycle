package com.bancobogota.customersKata.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", unique = true, nullable = false)
    private String username;

    @Column(name="password", unique = true, nullable = false)
    private String password;

    @Column(name="rol", unique = true, nullable = false)
    private String rol;
}
