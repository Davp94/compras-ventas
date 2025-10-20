package com.blumbit.compras_ventas.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.auth.dto.AuthRequest;
import com.blumbit.compras_ventas.auth.dto.AuthResponse;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse authentication(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword(),
                            null));
            Usuario usuario = usuarioRepository.findByEmail(authRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
            if (usuario.getEstado() == "INACTIVO") {
                throw new RuntimeException("Usuario inactivo");
            }
            String accesToken = jwtService.generateToken(usuario);
            String refreshToken = jwtService.generateRefreshToken(usuario);
            return AuthResponse.builder()
                    .token(accesToken)
                    .refreshToken(refreshToken)
                    .identifier(usuario.getId())
                    .expiration(expiration)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AuthResponse refreshToken(String authentication) {
        try {
            if (authentication == null || !authentication.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid header");
            }
            String refreshToken = authentication.substring(7);
            String userEmail = jwtService.extractUsername(refreshToken);
            Usuario usuario = usuarioRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
            if (!jwtService.isTokenValid(refreshToken, usuario)) {
                throw new RuntimeException("Token invalido");
            }
            String accesToken = jwtService.generateToken(usuario);
            return AuthResponse.builder()
                    .token(accesToken)
                    .identifier(usuario.getId())
                    .expiration(expiration)
                    .build();
        } catch (Exception e) {
            throw e;
        }

    }

}
