package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.dto.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.PostDTOConverter;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.repository.PostRepository;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long, PostRepository> {

    private final PostRepository postRepository;
    private final PostDTOConverter postDTOConverter;

    public Post addPost (CreatePostDTO postDTO){
        Post post = postDTOConverter.convertToPost(postDTO);
        return post;
    }
}
