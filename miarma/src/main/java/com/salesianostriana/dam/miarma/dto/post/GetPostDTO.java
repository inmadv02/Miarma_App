package com.salesianostriana.dam.miarma.dto.post;

import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostDTO {

    private Long id;
    private String titulo, texto, urlFoto;


    @NotNull(message = "{campo.not.null}")
    private Visibilidad visibilidad;
}
