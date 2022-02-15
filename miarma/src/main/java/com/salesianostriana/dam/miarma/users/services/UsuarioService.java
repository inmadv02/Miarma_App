package com.salesianostriana.dam.miarma.users.services;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.services.StorageService;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.users.dto.CreateUsuarioDto;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return this.repositorio.findFirstByNickname(nickname)
                .orElseThrow(()-> new UsernameNotFoundException(nickname + " no encontrado"));
    }

    public Usuario save(CreateUsuarioDto nuevoUsuario, MultipartFile file) throws IOException {

        storageService.scaleImage(file, 300);

        String fileName = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();


        Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoUsuario.getPassword()))
                    .fechaNacimiento(nuevoUsuario.getFechaNacimiento())
                    .foto(uri)
                    .fullname(nuevoUsuario.getFullname())
                    .nickname(nuevoUsuario.getNickname())
                    .email(nuevoUsuario.getEmail())
                    .visibilidad(nuevoUsuario.getVisibilidad())
                    .build();
            return save(usuario);

    }

    public List<Post> myPosts(Usuario usuario){

        usuario = repositorio.findFirstByNickname(usuario.getNickname()).get();

        return usuario.getPublicaciones();
    }

}
