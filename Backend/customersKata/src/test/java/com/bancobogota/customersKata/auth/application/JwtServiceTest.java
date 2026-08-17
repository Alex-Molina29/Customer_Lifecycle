package com.bancobogota.customersKata.auth.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService,"secret","unaClaveQueSeUsaParaPruebasYSoloParaPruebas");
        ReflectionTestUtils.setField(jwtService, "expirationMs", 3600000L);
    }

    @Test
    void deberiaGenerarYValidarUnTokenCorrectamente() {
        String token = jwtService.generateToken("prueba");

        assertThat(token).isNotBlank();
        assertThat(jwtService.isTokenValid(token)).isTrue();
        assertThat(jwtService.extractUsername(token)).isEqualTo("prueba");
    }

    @Test
    void deberiaRechazarUnTokenInvalido() {
        // Act + Assert
        assertThat(jwtService.isTokenValid("esto.no.es.un.token.valido")).isFalse();
    }
}
