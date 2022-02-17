package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUsuarioMoreDetailsDTO {

    private UUID id;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String nickname;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String nombre;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    @Email(message = "{email.format}")
    private String email;

    private String descripcion;

    private String foto;

    @NotNull(message = "{campo.not.null}")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String password;


    @NotNull(message = "{campo.not.null}")
    private Visibilidad visibilidad;
}
