package com.salesianostriana.dam.miarma.dto.post;

import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostDTO {

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String titulo, texto;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    @URL(message = "No tiene un formato correcto")
    private String urlFoto;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private Visibilidad visibilidad;
}
