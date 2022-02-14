package com.salesianostriana.dam.miarma.users.controller;

import com.salesianostriana.dam.miarma.users.dto.CreateUsuarioDto;
import com.salesianostriana.dam.miarma.users.dto.GetUsuarioDto;
import com.salesianostriana.dam.miarma.users.dto.UserDTOConverter;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UserDTOConverter userDtoConverter;


    @PostMapping("/auth/register")
    public ResponseEntity<GetUsuarioDto> nuevoUsuario(@RequestPart CreateUsuarioDto nuevoUsuario,
                                                      @RequestPart MultipartFile file) {
        Usuario usuarioGuardado = usuarioService.save(nuevoUsuario, file);

        if (!usuarioGuardado.getNickname().isEmpty())
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(usuarioGuardado));
        else
            return ResponseEntity.badRequest().build();

    }


}
