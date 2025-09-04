package com.blumbit.compras_ventas.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;

    private final RolRepository rolRepository;
    //TODO add repositories

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();
        if(usuarioRepository.count() == 0){
            for(int i = 0; i < 10; i++){
                Usuario usuario = usuarioRepository.save(Usuario.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().firstName())
                .password("$2a$12$Dxqj062yoyT5klc7MBOXLO.B0UJg663Vpsdil5ctV0W.50MsZqN7m")
                .build());
                //TODO add roles & permissions
            }
        }
    }
}
