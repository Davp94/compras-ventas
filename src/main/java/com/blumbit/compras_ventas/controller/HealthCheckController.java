package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/status")
public class HealthCheckController {

    @GetMapping
    public String apiStatus() {
        return "Servicio ejecutandose exitosamente";
    }
}
