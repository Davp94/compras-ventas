package com.blumbit.compras_ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blumbit.compras_ventas.dto.request.UsuarioRequest;
import com.blumbit.compras_ventas.dto.response.UsuarioResponse;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.blumbit.compras_ventas.service.spec.UsuarioService;

import jakarta.persistence.EntityManager;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;

    private final PersonaRepository personaRepository;
    private final EntityManager entityManager;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PersonaRepository personaRepository, EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<UsuarioResponse> findAllUsuarios() {
        try {
          return usuarioRepository.findAll().stream().map(usuario->UsuarioResponse.fromEntity(usuario)).collect(Collectors.toList());
        } catch (Exception e) {
           throw e;
        }
    }

    @Override
    public UsuarioResponse findUsuarioById(Long id) {
        try {
            // Usuario usuarioRetrieved = new Usuario();
            // try {
            //     usuarioRetrieved = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
            // } catch (Exception e) {
            //     usuarioRetrieved = usuarioRepository.findById((long)0).get();
            // }
            //add complex logic
          return UsuarioResponse.fromEntity(usuarioRepository.findById(id).get());
        } catch (Exception e) {
           throw e;
        }
      
    }

    @Override
    @Transactional
    public UsuarioResponse crearUsuario(UsuarioRequest usuarioRequest) {
        try {
            Usuario usuarioToCreate = UsuarioRequest.toEntityUsuario(usuarioRequest);
            usuarioToCreate.setName(generateUsername(usuarioRequest.getDocumentoIdentidad(), usuarioRequest.getNombres()));
            Usuario usuarioCreated = usuarioRepository.save(usuarioToCreate);
            Persona personaToCreate = UsuarioRequest.toEntityPersona(usuarioRequest);
            personaToCreate.setUsuario(usuarioCreated);
            personaRepository.save(personaToCreate);
            return UsuarioResponse.fromEntity(usuarioCreated);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UsuarioResponse updateUsuario(Long id, UsuarioRequest usuarioRequest) {
         try {
            Usuario usuarioRetrieved = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuarioRetrieved.setEmail(usuarioRequest.getCorreo());
            if(usuarioRequest.getRoles() != null){
            usuarioRetrieved.setRoles(usuarioRequest.getRoles().stream().map(rolId->
                entityManager.getReference(Rol.class, rolId)
            ).collect(Collectors.toSet()));
            }
            Persona personaRetrieved = personaRepository.findById(usuarioRetrieved.getPersona().getId()).orElseThrow(() -> new RuntimeException("Persona no encontrado"));
            personaRetrieved.setDireccion(usuarioRequest.getDireccion());
            personaRetrieved.setTelefono(usuarioRequest.getTelefono());
            personaRepository.save(personaRetrieved);
            return UsuarioResponse.fromEntity(usuarioRepository.save(usuarioRetrieved));
        } catch (Exception e) {
           throw e;
        }
    }

    @Override
    public UsuarioResponse deleteUsuarioById(Long id) {
        try {
            //BORRADO LOGICO
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuario.setEstado("INACTIVO");
            return UsuarioResponse.fromEntity(usuarioRepository.save(usuario));
        } catch (Exception e) {
           throw e;
        }
    }

    private String generateUsername(String documentoIdentidad, String nombres){
        return "";
    }

    @Override
    public UsuarioResponse enableUsuarioById(Long id) {
        try {
            //ENABLE
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            //validate estado usuario
            if(!usuario.getEstado().equals("INACTIVO")){
                throw new RuntimeException("Usuario no se encuentra en un estado para activarlo");
            }
            usuario.setEstado("ACTIVO");
            return UsuarioResponse.fromEntity(usuarioRepository.save(usuario));
        } catch (Exception e) {
           throw e;
        }
    }



    
}
