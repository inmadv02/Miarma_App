package com.salesianostriana.dam.miarma.users.controller;

import com.salesianostriana.dam.miarma.users.dto.*;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.services.UsuarioService;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UserDTOConverter userDtoConverter;


    @PostMapping("/auth/register")
    public ResponseEntity<GetUsuarioDto> nuevoUsuario(@Valid @RequestPart CreateUsuarioDto nuevoUsuario,
                                                      @RequestPart MultipartFile file) throws IOException, ImageException, VideoException {
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

    @GetMapping("/myprofile")
    public ResponseEntity<GetUsuarioWithPostsImagesDto> myProfile(@AuthenticationPrincipal Usuario usuario) {
        GetUsuarioWithPostsImagesDto getUsuarioDto = usuarioService.myProfile(usuario);

        if (getUsuarioDto == null) {
            return ResponseEntity.badRequest().build();

        } else {
            return ResponseEntity.ok().body(getUsuarioDto);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Page<GetUsuarioAdminDto>> allAdmins(@AuthenticationPrincipal Usuario usuario,
                                                              @PageableDefault(page=0, size=9) Pageable pageable){

        Page<GetUsuarioAdminDto> lista = usuarioService
                                                .allAdminUsers(pageable)
                                                .map(userDtoConverter::convertUserEntityToGetUserAdminDto);

        return ResponseEntity.ok().body(lista);
    }

}
