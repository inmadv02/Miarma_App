package com.salesianostriana.dam.miarma.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String email;
    private String fullName;
    private String foto;
    private String token;

}