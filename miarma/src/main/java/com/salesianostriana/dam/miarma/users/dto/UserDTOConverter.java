package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDTOConverter {

    private final UsuarioRepository usuarioRepository;

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

    public GetUsuarioAdminDto convertUserEntityToGetUserAdminDto(Usuario usuario) {
        return GetUsuarioAdminDto.builder()
                .id(usuario.getId())
                .nickname(usuario.getNickname())
                .foto(usuario.getFoto())
                .descripcion(usuario.getBiografia() == null ? " " : usuario.getBiografia())
                .isAdmin(usuario.isIdAdmin())
                .build();


    }

    public GetUsuarioWithPostsImagesDto convertToGetUsuarioWithPhotos(Usuario usuario){
        return GetUsuarioWithPostsImagesDto
                        .builder()
                        .id(usuario.getId())
                        .nickname(usuario.getNickname())
                        .fullname(usuario.getFullname())
                        .foto(usuario.getFoto())
                        .descripcion(usuario.getBiografia() == null ? " " : usuario.getBiografia())
                        .numeroSeguidores(usuarioRepository.seguidoresDeUnUsuario(usuario).size())
                        .visibilidad(usuario.getVisibilidad().getTexto())
                        .numeroSiguiendo(usuarioRepository.publicacionesUsuario(usuario).size())
                        .publicaciones(usuarioRepository.publicacionesUsuario2(usuario).stream().map(Post::getUrlFichero1).collect(Collectors.toList()))
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
