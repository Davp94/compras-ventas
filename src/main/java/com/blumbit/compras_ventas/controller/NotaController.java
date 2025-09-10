package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.request.NotaRequest;
import com.blumbit.compras_ventas.dto.response.ClienteResponse;
import com.blumbit.compras_ventas.dto.response.NotaResponse;
import com.blumbit.compras_ventas.service.spec.NotaService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/nota")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;

    @GetMapping
    public ResponseEntity<List<NotaResponse>> findAllNotas() {
        return ResponseEntity.ok().body(notaService.findAllNotas());
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ClienteResponse>> getAllClientes() {
        return ResponseEntity.ok().body(notaService.findAllClientes());
    }
    

    @PostMapping
    public ResponseEntity<NotaResponse> createNota(@RequestBody NotaRequest notaRequest) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(notaService.createNota(notaRequest));
    }

}
