package com.salesianostriana.dam.miarma.security;


import com.salesianostriana.dam.miarma.security.dto.JwtUserResponse;
import com.salesianostriana.dam.miarma.security.dto.LoginDTO;
import com.salesianostriana.dam.miarma.security.jwt.JwtProvider;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getNickname(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));

    }

    @GetMapping("/me")
    public ResponseEntity<?> miPerfil(@AuthenticationPrincipal Usuario user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }


    private JwtUserResponse convertUserToJwtUserResponse(Usuario user, String jwt) {
        return JwtUserResponse.builder()
                .fullName(user.getFullname())
                .email(user.getEmail())
                .foto(user.getFoto())
                .token(jwt)
                .build();
    }


}

