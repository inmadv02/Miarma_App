package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.dto.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.PostDTOConverter;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.repository.PostRepository;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long, PostRepository> {

    private final PostRepository postRepository;
    private final PostDTOConverter postDTOConverter;
    private final StorageService storageService;

    public Post addPost (CreatePostDTO postDTO, MultipartFile file){

        String fileName = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        postDTO.setUrlFoto(uri);

        Post post = postDTOConverter.convertToPost(postDTO);

        postRepository.save(post);

        return post;
    }
}
