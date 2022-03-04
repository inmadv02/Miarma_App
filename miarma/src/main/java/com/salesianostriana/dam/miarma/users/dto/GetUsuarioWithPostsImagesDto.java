package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.model.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUsuarioWithPostsImagesDto {

    private UUID id;
    private String nickname;
    private String fullname;
    private String descripcion;
    private String foto;
    private List<String> publicaciones;
    private int numeroSeguidores;
    private int numeroSiguiendo;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String visibilidad;
}
