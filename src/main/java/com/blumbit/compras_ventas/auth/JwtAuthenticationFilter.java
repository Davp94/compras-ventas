package com.blumbit.compras_ventas.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getServletPath().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(userEmail == null || authentication != null){
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if(usuario.getEstado() == "INACTIVO"){
            throw new RuntimeException("El Usuario no tiene permisos para ingresar al sistema");
        }

        if(jwtService.isTokenValid(jwt, usuario)){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

}
