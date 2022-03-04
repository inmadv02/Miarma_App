package com.salesianostriana.dam.miarma.users.repository;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByNickname(String nickname);

    @Query("select u.siguiendo from Usuario u where u= ?1")
    List<Usuario> publicacionesUsuario(Usuario usuario);

    @Query("select u.seguidores from Usuario u where u= ?1")
    List<Usuario> seguidoresDeUnUsuario(Usuario usuario);

    @Query("select u.publicaciones from Usuario u where u= ?1")
    List<Post> publicacionesUsuario2(Usuario usuario);

}
