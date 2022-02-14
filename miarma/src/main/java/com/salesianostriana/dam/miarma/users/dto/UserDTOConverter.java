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
                .numeroSiguiendo(usuario.getSiguiendo().size())
                .publicaciones(usuario.getPublicaciones())
                .build();


    }


}
