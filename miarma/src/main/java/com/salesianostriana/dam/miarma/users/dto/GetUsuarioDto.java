package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUsuarioDto {

    private UUID id;
    private String nickname;
    private String descripcion;
    private String foto;
    private List<Post> publicaciones;
    private int numeroSeguidores;
    private int numeroSiguiendo;

}
