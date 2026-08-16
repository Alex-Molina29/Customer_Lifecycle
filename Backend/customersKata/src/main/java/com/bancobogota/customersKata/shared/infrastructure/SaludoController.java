package com.bancobogota.customersKata.shared.infrastructure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class SaludoController {

    // Pruebas de funcionamiento, se debe eliminar eventualmente

    @GetMapping()
    public String rutaBase() {
        return "Backend Funcional";
    }
    

    @GetMapping("/hola")
    public String holaMundo() {
        return "Hola mundo";
    }
}
