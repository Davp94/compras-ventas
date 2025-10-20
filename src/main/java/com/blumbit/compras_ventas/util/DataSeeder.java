package com.blumbit.compras_ventas.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Almacen;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.Categoria;
import com.blumbit.compras_ventas.entity.Cliente;
import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.AlmacenProductoRepository;
import com.blumbit.compras_ventas.repository.AlmacenRepository;
import com.blumbit.compras_ventas.repository.CategoriaRepository;
import com.blumbit.compras_ventas.repository.ClienteRepository;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.SucursalRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

        private final UsuarioRepository usuarioRepository;
        private final PersonaRepository personaRepository;
        private final RolRepository rolRepository;
        private final PermisoRepository permisoRepository;
        private final CategoriaRepository categoriaRepository;
        private final ProductoRepository productoRepository;
        private final SucursalRepository sucursalRepository;
        private final AlmacenRepository almacenRepository;
        private final AlmacenProductoRepository almacenProductoRepository;
        private final ClienteRepository clienteRepository;
        private final PasswordEncoder passwordEncoder;
        // TODO add repositories

        @Override
        public void run(ApplicationArguments args) throws Exception {
                Faker faker = new Faker();
                Random random = new Random();

                List<String> generos = List.of("MASCULINO", "FEMENINO", "OTRO", "PREFIERO_NO_DECIR");
                List<String> tipos = List.of("DNI", "PASAPORTE", "CEDULA", "CARNET_EXTRANJERIA", "RUC");
                List<String> paises = List.of(
                                "Peruana", "Boliviana", "Argentina", "Chilena", "Colombiana",
                                "Ecuatoriana", "Brasileña", "Venezolana", "Mexicana", "Española");

                // Step 1: Create Permissions
                Set<Permiso> allPermisos = new HashSet<>();
                if (permisoRepository.count() == 0) {
                        List<String> resources = List.of("usuarios", "roles", "permisos", "productos", "clientes",
                                        "ventas",
                                        "reportes");
                        List<String> actions = List.of("create", "read", "update", "delete", "list");

                        for (String resource : resources) {
                                for (String action : actions) {
                                        Permiso permiso = Permiso.builder()
                                                        .name(action.toUpperCase() + "_" + resource.toUpperCase())
                                                        .resource(resource)
                                                        .action(action)
                                                        .detail("Permiso para " + action + " en " + resource)
                                                        .build();
                                        allPermisos.add(permisoRepository.save(permiso));
                                }
                        }
                        System.out.println("✓ Permisos creados: " + allPermisos.size());
                }

                // Step 2: Create Roles with Permissions
                Map<String, Set<Permiso>> rolesMap = new HashMap<>();
                if (rolRepository.count() == 0) {
                        // Admin role - all permissions
                        Rol adminRol = Rol.builder()
                                        .nombre("ADMIN")
                                        .descripcion("Administrador con acceso completo al sistema")
                                        .permisos(new HashSet<>(allPermisos))
                                        .build();
                        adminRol = rolRepository.save(adminRol);
                        rolesMap.put("ADMIN", adminRol.getPermisos());

                        // Manager role - most permissions except critical ones
                        Set<Permiso> managerPermisos = allPermisos.stream()
                                        .filter(p -> !p.getAction().equals("delete")
                                                        || !p.getResource().equals("usuarios"))
                                        .collect(Collectors.toSet());
                        Rol managerRol = Rol.builder()
                                        .nombre("MANAGER")
                                        .descripcion("Gerente con permisos de gestión")
                                        .permisos(managerPermisos)
                                        .build();
                        managerRol = rolRepository.save(managerRol);
                        rolesMap.put("MANAGER", managerRol.getPermisos());

                        // User role - read permissions
                        Set<Permiso> userPermisos = allPermisos.stream()
                                        .filter(p -> p.getAction().equals("read") || p.getAction().equals("list"))
                                        .collect(Collectors.toSet());
                        Rol userRol = Rol.builder()
                                        .nombre("USER")
                                        .descripcion("Usuario estándar con permisos de lectura")
                                        .permisos(userPermisos)
                                        .build();
                        userRol = rolRepository.save(userRol);
                        rolesMap.put("USER", userRol.getPermisos());

                        // Seller role - sales related permissions
                        Set<Permiso> sellerPermisos = allPermisos.stream()
                                        .filter(p -> p.getResource().equals("ventas") ||
                                                        p.getResource().equals("clientes") ||
                                                        p.getResource().equals("productos"))
                                        .collect(Collectors.toSet());
                        Rol sellerRol = Rol.builder()
                                        .nombre("SELLER")
                                        .descripcion("Vendedor con permisos de ventas")
                                        .permisos(sellerPermisos)
                                        .build();
                        sellerRol = rolRepository.save(sellerRol);
                        rolesMap.put("SELLER", sellerRol.getPermisos());

                        System.out.println("✓ Roles creados: " + rolesMap.size());
                }

                // Step 3: Create Users with Roles
                if (usuarioRepository.count() == 0) {
                        List<Rol> availableRoles = rolRepository.findAll();
                        List<String> roleNames = List.of("ADMIN", "MANAGER", "USER", "SELLER");

                        for (int i = 0; i < 10; i++) {
                                int minAge = 18;
                                int maxAge = 80;
                                int age = minAge + random.nextInt(maxAge - minAge + 1);

                                // Assign 1-3 random roles to each user
                                Set<Rol> userRoles = new HashSet<>();
                                int numRoles = 1 + random.nextInt(3);
                                List<Rol> shuffledRoles = new ArrayList<>(availableRoles);
                                Collections.shuffle(shuffledRoles);
                                userRoles.addAll(shuffledRoles.subList(0, Math.min(numRoles, shuffledRoles.size())));

                                Usuario usuario = Usuario.builder()
                                                .email(faker.internet().emailAddress())
                                                .name(faker.name().fullName())
                                                .password(passwordEncoder.encode("123456")) 
                                                .estado("ACTIVO")
                                                .roles(userRoles)
                                                .build();

                                usuario = usuarioRepository.save(usuario);
                                Persona persona = Persona.builder()
                                                .apellidos(faker.name().lastName())
                                                .nombres(faker.name().firstName())
                                                .direccion(faker.address().fullAddress())
                                                .fechaNacimiento(LocalDate.now().minusYears(age)
                                                                .minusDays(random.nextInt(365)))
                                                .genero(generos.get(random.nextInt(generos.size())))
                                                .telefono(faker.phoneNumber().cellPhone())
                                                .usuario(usuario)
                                                .tipoDocumento(tipos.get(random.nextInt(tipos.size())))
                                                .nacionalidad(paises.get(random.nextInt(paises.size())))
                                                .documentoIdentidad(faker.number().digits(11))
                                                .build();
                                persona = personaRepository.save(persona);
                        }
                }

                if (categoriaRepository.count() == 0) {
                        List<Categoria> categorias = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                                Categoria categoriaSaved = categoriaRepository.save(Categoria.builder()
                                                .descripcion(faker.lorem().sentence(10))
                                                .nombre(faker.commerce().department())
                                                .build());
                                categorias.add(categoriaSaved);
                        }

                        for (int i = 0; i < 1000; i++) {
                                Categoria categoria = categorias.get(random.nextInt(categorias.size()));
                                productoRepository.save(Producto.builder()
                                                .estado("activo")
                                                .codigoBarra(faker.code().ean13())
                                                .descripcion(faker.commerce().productName() + " - "
                                                                + faker.lorem().sentence(5))
                                                .imageUrl("image-product-example.png")
                                                .marca(faker.company().name())
                                                .nombre(faker.commerce().productName())
                                                .precioVentaActual(BigDecimal
                                                                .valueOf(faker.number().randomDouble(2, 1, 1000)))
                                                .categoria(categoria)
                                                .unidadMedida(getRandomUnidadMedida(faker)).build());
                        }
                }
                if (sucursalRepository.count() == 0 && almacenRepository.count() == 0) {
                        // CREATE SUCURSALES
                        List<Sucursal> sucursales = new ArrayList<>();
                        String[] ciudades = { "Santa Cruz", "La Paz", "Cochabamba", "Tarija", "Potosí" };

                        for (int i = 0; i < 5; i++) {
                                Sucursal sucursal = Sucursal.builder()
                                                .nombre("Sucursal " + faker.company().name())
                                                .direccion(faker.address().fullAddress())
                                                .telefono(faker.phoneNumber().cellPhone())
                                                .ciudad(ciudades[i])
                                                .build();

                                Sucursal sucursalSaved = sucursalRepository.save(sucursal);
                                sucursales.add(sucursalSaved);
                        }
                        // Create 10 Almacenes (2 per sucursal)
                        List<Almacen> almacenes = new ArrayList<>();
                        String[] tiposAlmacen = { "Principal", "Secundario", "Temporal", "Especial", "General" };

                        for (int i = 0; i < 10; i++) {
                                // Distribute almacenes evenly among sucursales (2 per sucursal)
                                Sucursal sucursal = sucursales.get(i / 2);

                                String codigo = "ALM-" + String.format("%03d", i + 1);
                                String tipoAlmacen = tiposAlmacen[random.nextInt(tiposAlmacen.length)];

                                Almacen almacen = Almacen.builder()
                                                .nombre("Almacén " + tipoAlmacen + " " + sucursal.getCiudad())
                                                .codigo(codigo)
                                                .descripcion(faker.lorem().sentence(6) + " ubicado en "
                                                                + sucursal.getCiudad())
                                                .sucursal(sucursal)
                                                .build();

                                Almacen almacenSaved = almacenRepository.save(almacen);
                                almacenes.add(almacenSaved);
                        }
                        // Create AlmacenProducto relations
                        // Get all existing products
                        List<Producto> productos = productoRepository.findAll();
                        if (!productos.isEmpty()) {
                                for (Almacen almacen : almacenes) {
                                        for (int i = 0; i < 200; i++) {
                                                Producto producto = productos.get(random.nextInt(productos.size()));

                                                // Create AlmacenProducto relation
                                                AlmacenProducto almacenProducto = AlmacenProducto.builder()
                                                                .cantidad(faker.number().numberBetween(0, 500))
                                                                .fechaActualizacion(LocalDateTime.now().minusDays(
                                                                                faker.number().numberBetween(0, 90)))
                                                                .almacen(almacen)
                                                                .producto(producto)
                                                                .build();

                                                almacenProductoRepository.save(almacenProducto);
                                        }
                                }
                        }
                }

                if (clienteRepository.count() <= 0) {
                        String[] tiposComerciales = {
                                        "EMPRESA", "COOPERATIVA", "FUNDACION", "ONG",
                                        "ASOCIACION", "CORPORACION", "SOCIEDAD", "MICROEMPRESA"
                        };
                        for (int i = 0; i < 10; i++) {

                                Cliente cliente = Cliente.builder()
                                                .tipo(tiposComerciales[random.nextInt(tiposComerciales.length)])
                                                .razonSocial(faker.company().name() + " " +
                                                                faker.options().option("S.A.", "S.R.L.", "LTDA", "CIA"))
                                                .estado("ACTIVO")
                                                .telefono(generatePhoneNumber(faker))
                                                .direccion(faker.address().streetAddress() + ", " +
                                                                faker.address().city())
                                                .correo(faker.internet().emailAddress())
                                                .build();

                                clienteRepository.save(cliente);
                        }
                }
        }

        private String getRandomUnidadMedida(Faker faker) {
                String[] unidades = { "kg", "g", "ml", "l", "pack", "unit" };
                return unidades[faker.number().numberBetween(0, unidades.length)];
        }

        private String generatePhoneNumber(Faker faker) {
                String[] formats = {
                                faker.phoneNumber().cellPhone(),
                                faker.phoneNumber().phoneNumber(),
                                "+591 " + faker.number().digits(8), // Bolivia format
                                faker.number().digits(7) + "-" + faker.number().digits(4)
                };

                Random random = new Random();
                return formats[random.nextInt(formats.length)];
        }
}
