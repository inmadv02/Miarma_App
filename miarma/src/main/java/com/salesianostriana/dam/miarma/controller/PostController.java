package com.salesianostriana.dam.miarma.controller;

import com.salesianostriana.dam.miarma.dto.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.GetPostDTO;
import com.salesianostriana.dam.miarma.dto.PostDTOConverter;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostDTOConverter postDTOConverter;

    @PostMapping("/")
    public ResponseEntity<GetPostDTO> createPost(@RequestBody CreatePostDTO createPostDTO){

        Post nuevoPost = postService.addPost(createPostDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postDTOConverter.covertToPostDTO(nuevoPost));

    }

}
