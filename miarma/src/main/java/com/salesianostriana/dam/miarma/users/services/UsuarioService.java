package com.salesianostriana.dam.miarma.users.services;

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

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return this.repositorio.findFirstByNickname(nickname)
                .orElseThrow(()-> new UsernameNotFoundException(nickname + " no encontrado"));
    }

    public Usuario save(CreateUsuarioDto nuevoUsuario) {

            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoUsuario.getPassword()))
                    .fechaNacimiento(nuevoUsuario.getFechaNacimiento())
                    .foto(nuevoUsuario.getAvatar())
                    .fullname(nuevoUsuario.getFullname())
                    .nickname(nuevoUsuario.getNickname())
                    .email(nuevoUsuario.getEmail())
                    .build();
            return save(usuario);

    }

}
