package com.salesianostriana.dam.miarma.dto;

import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostDTO {

    private String titulo, texto, urlFoto;
    private Visibilidad visibilidad;
}
