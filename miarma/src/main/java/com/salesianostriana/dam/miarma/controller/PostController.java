package com.salesianostriana.dam.miarma.controller;

import com.salesianostriana.dam.miarma.dto.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.GetPostDTO;
import com.salesianostriana.dam.miarma.dto.PostDTOConverter;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostDTOConverter postDTOConverter;
    private final UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<GetPostDTO> createPost(@RequestPart("createPostDTO") CreatePostDTO createPostDTO,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal Usuario usuario) throws IOException {

        Post nuevoPost = postService.addPost(createPostDTO, file, usuario);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postDTOConverter.covertToPostDTO(nuevoPost));

    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPostDTO> changePost(@RequestPart("post") GetPostDTO post,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal Usuario usuario,
                                                 @PathVariable Long id) throws IOException {

        Post postEditado = postService.editPost(post, id, file, usuario);

        return ResponseEntity.ok()
                             .body(postDTOConverter.covertToPostDTO(postEditado));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GetPostDTO>> myPosts(@AuthenticationPrincipal Usuario usuario){

        List<GetPostDTO> lista = usuarioService.myPosts(usuario)
                                                .stream()
                                                .map(postDTOConverter::covertToPostDTO)
                                                .collect(Collectors.toList());

        return ResponseEntity.ok().body(lista);
    }

}
