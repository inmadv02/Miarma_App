package com.salesianostriana.dam.miarma;

import com.salesianostriana.dam.miarma.model.FollowRequest;
import com.salesianostriana.dam.miarma.repository.FollowRequestRepository;
import com.salesianostriana.dam.miarma.services.FollowRequestService;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.users.controller.UsuarioController;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import com.salesianostriana.dam.miarma.users.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final FollowRequestService followRequestService;
    private final PostService postService;
    private final FollowRequestRepository followRequestRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void initData(){


        Usuario usuario1 = Usuario
                            .builder()
                            .fullname("José Jiménez Macías")
                            .visibilidad(Visibilidad.PUBLIC)
                            .password(passwordEncoder.encode("09876"))
                            .email("joselito@gmail.com")
                            .fechaNacimiento(LocalDate.of(2000, 6, 4))
                            .foto("https://img.blogs.es/anexom/wp-content/uploads/2021/12/perfil-1024x754.jpg")
                            .nickname("se_como_jose")
                            .publicaciones(new ArrayList<>())
                            .build();

        Usuario usuario2 = Usuario
                            .builder()
                            .fullname("Marina Gómez Vargas")
                            .visibilidad(Visibilidad.PRIVATE)
                            .password(passwordEncoder.encode("876543"))
                            .email("marinagv@gmail.com")
                            .fechaNacimiento(LocalDate.of(1998, 3, 24))
                            .foto("https://holatelcel.com/wp-content/uploads/2020/12/foto-perfil-whatsapp-.png")
                            .nickname("marina_mariae")
                            .publicaciones(new ArrayList<>())
                            .build();

        FollowRequest followRequest = FollowRequest
                .builder()
                .acepta(usuario1)
                .quiereSeguir(usuario2)
                .build();

        followRequest.addToUsuario(usuario1);
        followRequest.addToUsuario2(usuario2);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        followRequestRepository.save(followRequest);

    }


}
