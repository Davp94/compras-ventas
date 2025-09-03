package com.blumbit.compras_ventas.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;

    private String refreshToken;

    private long expiration;

}
