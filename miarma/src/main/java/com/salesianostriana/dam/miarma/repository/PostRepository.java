package com.salesianostriana.dam.miarma.repository;

import com.salesianostriana.dam.miarma.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByVisibilidad(String visibilidad);
}
