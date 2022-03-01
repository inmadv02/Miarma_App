package com.salesianostriana.dam.miarma.dto.post;

import com.salesianostriana.dam.miarma.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDTOConverter {


    public Post convertToPost(CreatePostDTO createPostDTO){
        return Post.builder()
                    .titulo(createPostDTO.getTitulo())
                    .descripcion(createPostDTO.getTexto())
                    .urlFichero1(createPostDTO.getUrlFoto())
                    .urlFichero2(createPostDTO.getUrlFoto2())
                    .visibilidad(createPostDTO.getVisibilidad())
                    .build();
    }

    public GetPostDTO covertToPostDTO(Post post){
        return GetPostDTO.builder()
                         .id(post.getPostId())
                         .texto(post.getDescripcion())
                         .urlFoto1(post.getUrlFichero1())
                         .urlFoto2(post.getUrlFichero2())
                         .titulo(post.getTitulo())
                         .nickname(post.getUsuarioPublicacion().getNickname())
                         .fotoPerfil(post.getUsuarioPublicacion().getFoto())
                         .likes(post.getLikes())
                         .visibilidad(post.getVisibilidad())
                         .build();
    }
}
