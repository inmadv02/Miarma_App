package com.salesianostriana.dam.miarma;

import com.salesianostriana.dam.miarma.dto.post.CreatePostDTO;
import com.salesianostriana.dam.miarma.model.FollowRequest;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.repository.FollowRequestRepository;
import com.salesianostriana.dam.miarma.repository.PostRepository;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final FollowRequestService followRequestService;
    private final PostService postService;
    private final PostRepository postRepository;
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
                            .idAdmin(true)
                            .biografia("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat")
                            .fechaNacimiento(LocalDate.of(2000, 6, 4))
                            .foto("https://img.blogs.es/anexom/wp-content/uploads/2021/12/perfil-1024x754.jpg")
                            .nickname("se_como_jose")
                            .publicaciones(new ArrayList<>())
                            .build();

        Usuario usuario2 = Usuario
                            .builder()
                            .fullname("Maria Gómez Vargas")
                            .visibilidad(Visibilidad.PRIVATE)
                            .idAdmin(false)
                            .biografia("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat")
                            .password(passwordEncoder.encode("876543"))
                            .email("mariagv@gmail.com")
                            .fechaNacimiento(LocalDate.of(1998, 3, 24))
                            .foto("https://holatelcel.com/wp-content/uploads/2020/12/foto-perfil-whatsapp-.png")
                            .nickname("maria_mariae")
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

        Post post = Post.builder()
                .visibilidad(Visibilidad.PUBLIC)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat")
                .usuarioPublicacion(usuario1)
                .urlFichero1("https://mott.pe/noticias/wp-content/uploads/2018/03/10-trucos-para-saber-c%C3%B3mo-tomar-fotos-profesionales-con-el-celular-portada-1280x720.jpg")
                .urlFichero2("https://mott.pe/noticias/wp-content/uploads/2018/03/10-trucos-para-saber-c%C3%B3mo-tomar-fotos-profesionales-con-el-celular-portada-1280x720.jpg")
                .titulo("Post 1")
                .build();


        Post post2 = Post.builder()
                .visibilidad(Visibilidad.PUBLIC)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat")
                .usuarioPublicacion(usuario1)
                .urlFichero1("https://mott.pe/noticias/wp-content/uploads/2018/03/10-trucos-para-saber-c%C3%B3mo-tomar-fotos-profesionales-con-el-celular-portada-1280x720.jpg")
                .urlFichero2("https://mott.pe/noticias/wp-content/uploads/2018/03/10-trucos-para-saber-c%C3%B3mo-tomar-fotos-profesionales-con-el-celular-portada-1280x720.jpg")
                .titulo("Post 2")
                .build();

        Post post3 = Post.builder()
                .visibilidad(Visibilidad.PUBLIC)
                .descripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat")
                .usuarioPublicacion(usuario2)
                .urlFichero1("https://mott.pe/noticias/wp-content/uploads/2018/03/10-trucos-para-saber-c%C3%B3mo-tomar-fotos-profesionales-con-el-celular-portada-1280x720.jpg")
                .urlFichero2("https://mott.pe/noticias/wp-content/uploads/2018/03/10-trucos-para-saber-c%C3%B3mo-tomar-fotos-profesionales-con-el-celular-portada-1280x720.jpg")
                .titulo("Post 3")
                .build();

        postRepository.saveAll(List.of(post, post2, post3));

    }


}
