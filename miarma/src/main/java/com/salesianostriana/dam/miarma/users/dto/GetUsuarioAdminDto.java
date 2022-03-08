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
public class GetUsuarioAdminDto {

    private UUID id;
    private String nickname;
    private String descripcion;
    private String foto;
    private boolean isAdmin;

}
