package com.blumbit.compras_ventas.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.auth.dto.AuthRequest;
import com.blumbit.compras_ventas.auth.dto.AuthResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return authService.authentication(authRequest);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestHeader String authentication) {
        return authService.refreshToken(authentication);
    }
    
    
}
