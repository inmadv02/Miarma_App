package com.salesianostriana.dam.miarma.users.dto;

import com.salesianostriana.dam.miarma.validation.simple.anotaciones.PasswordsMatch;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordsMatch(passwordField = "password",
        verifyPasswordField = "verifyPassword",
        message = "{passwords.do.not.match}")
public class CreateUsuarioDto {

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String nickname;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String avatar;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String fullname;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    @Email(message = "{email.format}")
    private String email;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String password;

    @NotBlank(message = "{campo.not.empty}")
    @NotNull(message = "{campo.not.null}")
    private String password2;
}
