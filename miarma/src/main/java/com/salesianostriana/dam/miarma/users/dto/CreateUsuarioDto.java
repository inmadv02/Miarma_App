package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import com.salesianostriana.dam.miarma.validation.simple.anotaciones.PasswordsMatch;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordsMatch(passwordField = "password",
        verifyPasswordField = "password2",
        message = "{passwords.do.not.match}")
public class CreateUsuarioDto {

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String nickname;

    private String avatar;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String fullname;

    @NotNull(message = "{campo.not.null}")
    @Past
    private LocalDate fechaNacimiento;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    @Email(message = "{email.format}")
    private String email;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String password;

    @NotBlank(message = "{campo.not.vacio}")
    @NotNull(message = "{campo.not.null}")
    private String password2;

    @NotNull(message = "{campo.not.null}")
    private Visibilidad visibilidad;
}
