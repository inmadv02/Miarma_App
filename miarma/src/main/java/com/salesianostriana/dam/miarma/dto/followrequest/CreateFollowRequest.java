package com.salesianostriana.dam.miarma.dto.followrequest;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFollowRequest {

    private String mensaje;
}
