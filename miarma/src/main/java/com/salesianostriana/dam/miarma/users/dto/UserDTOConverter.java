package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    public GetUsuarioDto convertUserEntityToGetUserDto(Usuario usuario) {
        return GetUsuarioDto.builder()
                .id(usuario.getId())
                .nickname(usuario.getNickname())
                .foto(usuario.getFoto())
                .descripcion(usuario.getBiografia() == null ? " " : usuario.getBiografia())
                .numeroSeguidores(usuario.getSeguidores().size())
                .visibilidad(usuario.getVisibilidad().getTexto())
                .numeroSiguiendo(usuario.getSiguiendo().size())
                .publicaciones(usuario.getPublicaciones())
                .build();


    }

    public GetUsuarioMoreDetailsDTO convertUserEntityToGetUserMoreDetailsDto(Usuario usuario) {
        return GetUsuarioMoreDetailsDTO.builder()
                .id(usuario.getId())
                .nickname(usuario.getNickname())
                .nombre(usuario.getFullname())
                .foto(usuario.getFoto())
                .descripcion(usuario.getBiografia() == null ? " " : usuario.getBiografia())
                .visibilidad(usuario.getVisibilidad())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .build();


    }


}
