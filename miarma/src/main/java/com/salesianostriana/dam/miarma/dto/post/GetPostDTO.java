package com.salesianostriana.dam.miarma.dto.post;

import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostDTO {

    private Long id;
    private String titulo, texto, urlFoto;
    private Visibilidad visibilidad;
}
