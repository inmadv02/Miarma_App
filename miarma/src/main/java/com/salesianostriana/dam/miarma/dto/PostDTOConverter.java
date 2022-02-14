package com.salesianostriana.dam.miarma.dto;

import com.salesianostriana.dam.miarma.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDTOConverter {

    public Post convertToPost(CreatePostDTO createPostDTO){
        return Post.builder()
                    .titulo(createPostDTO.getTitulo())
                .descripcion(createPostDTO.getTexto())
                .urlFichero(createPostDTO.getUrlFoto())
                .visibilidad(createPostDTO.getVisibilidad())
                    .build();
    }

    public GetPostDTO covertToPostDTO(Post post){
        return GetPostDTO.builder()
                         .id(post.getPostId())
                         .texto(post.getDescripcion())
                         .urlFoto(post.getUrlFichero())
                         .titulo(post.getTitulo())
                         .visibilidad(post.getVisibilidad())
                         .build();
    }
}
