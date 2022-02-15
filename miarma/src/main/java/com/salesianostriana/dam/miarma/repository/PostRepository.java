package com.salesianostriana.dam.miarma.repository;

import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByVisibilidad(Visibilidad visibilidad);

    List<Post> findByUsuarioNickname(String nickname);
}
