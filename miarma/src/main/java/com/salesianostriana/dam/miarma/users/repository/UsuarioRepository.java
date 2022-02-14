package com.salesianostriana.dam.miarma.users.repository;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByNickname(String nickname);

    List<Post> findByVisibilidad(String visibilidad);

   /* @Query("select * from usuario left join post p where p.visibilidad=PUBLIC")
    List<Post> todasLasPublicacionesPublicas();*/

}
