package com.blumbit.compras_ventas.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    //TODO add repositories

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        List<String> generos = List.of("MASCULINO", "FEMENINO", "OTRO", "PREFIERO_NO_DECIR");
        List<String> tipos = List.of("DNI", "PASAPORTE", "CEDULA", "CARNET_EXTRANJERIA", "RUC");
          List<String> paises = List.of(
            "Peruana", "Boliviana", "Argentina", "Chilena", "Colombiana", 
            "Ecuatoriana", "Brasileña", "Venezolana", "Mexicana", "Española"
        );
        if(usuarioRepository.count() == 0){
            for(int i = 0; i < 10; i++){
                  int minAge = 18;
                int maxAge = 80;
                int age = minAge + random.nextInt(maxAge - minAge + 1);
                Persona persona = personaRepository.save(Persona.builder()
                .apellidos(faker.name().firstName())
                .nombres(faker.name().lastName())
                .direccion(faker.address().fullAddress())
                .fechaNacimiento(LocalDate.now().minusYears(age).minusDays(random.nextInt(365)))
                .genero(generos.get(random.nextInt(generos.size())))
                .telefono(faker.phoneNumber().cellPhone())
                .tipoDocumento(tipos.get(random.nextInt(tipos.size())))
                .nacionalidad(paises.get(random.nextInt(paises.size())))
                .documentoIdentidad(faker.number().digits(11))
                .build());
                Usuario usuario = usuarioRepository.save(Usuario.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().firstName())
                .password("$2a$12$Dxqj062yoyT5klc7MBOXLO.B0UJg663Vpsdil5ctV0W.50MsZqN7m")
                .persona(persona)
                .build());
                //TODO add roles & permissions
            }
        }
    }
}
