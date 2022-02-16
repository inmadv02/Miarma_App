package com.salesianostriana.dam.miarma.repository;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByVisibilidad(Visibilidad visibilidad);

    List<Post> findByUsuarioPublicacionAndVisibilidad(Usuario usuario, Visibilidad visibilidad);

   /* @Query("select p from Post p join Usuario u on p.usuarioPublicacion=u WHERE p.visibilidad=PUBLIC")
    List<Post>*/


}
