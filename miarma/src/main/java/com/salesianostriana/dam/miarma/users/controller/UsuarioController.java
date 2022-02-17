package com.salesianostriana.dam.miarma.users.controller;

import com.salesianostriana.dam.miarma.users.dto.CreateUsuarioDto;
import com.salesianostriana.dam.miarma.users.dto.GetUsuarioDto;
import com.salesianostriana.dam.miarma.users.dto.GetUsuarioMoreDetailsDTO;
import com.salesianostriana.dam.miarma.users.dto.UserDTOConverter;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.services.UsuarioService;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UserDTOConverter userDtoConverter;


    @PostMapping("/auth/register")
    public ResponseEntity<GetUsuarioDto> nuevoUsuario(@Valid @RequestPart CreateUsuarioDto nuevoUsuario,
                                                      @RequestPart MultipartFile file) throws IOException, ImageException {
        Usuario usuarioGuardado = usuarioService.save(nuevoUsuario, file);

        if (usuarioGuardado == null) {
           return ResponseEntity.badRequest().build();

        } else {
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(usuarioGuardado));
        }
    }

    @PutMapping("/profile/me")
    public ResponseEntity<GetUsuarioMoreDetailsDTO> editProfile(@Valid @RequestPart GetUsuarioMoreDetailsDTO dto,
                                                                @RequestPart MultipartFile file,
                                                                @AuthenticationPrincipal Usuario usuario) throws IOException, VideoException {

        Usuario editado = usuarioService.editProfile(usuario, dto, file);

        return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserMoreDetailsDto(editado));

    }


}
