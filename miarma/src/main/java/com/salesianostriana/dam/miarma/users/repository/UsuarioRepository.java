package com.salesianostriana.dam.miarma.users.repository;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByNickname(String nickname);
}
