package com.salesianostriana.dam.miarma.controller;

import com.salesianostriana.dam.miarma.dto.post.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.post.GetPostDTO;
import com.salesianostriana.dam.miarma.dto.post.PostDTOConverter;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import com.salesianostriana.dam.miarma.users.services.UsuarioService;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostDTOConverter postDTOConverter;
    private final UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<GetPostDTO> createPost(@Valid @RequestPart("createPostDTO") CreatePostDTO createPostDTO,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal Usuario usuario) throws IOException, VideoException {

        Post nuevoPost = postService.addPost(createPostDTO, file, usuario);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postDTOConverter.covertToPostDTO(nuevoPost));

    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPostDTO> changePost(@Valid @RequestPart("post") GetPostDTO post,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal Usuario usuario,
                                                 @PathVariable Long id) throws IOException, VideoException {

        Post postEditado = postService.editPost(post, id, file, usuario);

        return ResponseEntity.ok()
                             .body(postDTOConverter.covertToPostDTO(postEditado));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<GetPostDTO>> myPosts(@AuthenticationPrincipal Usuario usuario, Pageable pageable){

        Page<GetPostDTO> lista = usuarioService.myPosts(usuario, pageable)
                                                .map(postDTOConverter::covertToPostDTO);

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/public")
    public ResponseEntity<Page<GetPostDTO>> findAllPublicPosts(@PageableDefault(page=0, size=9) Pageable pageable){

        Page<GetPostDTO> lista = postService
                                        .findAllPublicPost(pageable)
                                        .map(postDTOConverter::covertToPostDTO);

        return ResponseEntity.ok().body(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario) throws FileNotFoundException {

        postService.deletePost(id, usuario);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDTO> postsPorId(@PathVariable("id") Long id, @AuthenticationPrincipal Usuario usuario){

        Post post = postService.findOne(id, usuario);

        if(post.getPostId()==null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok().body(postDTOConverter.covertToPostDTO(post));
        }
    }

    @GetMapping
    public ResponseEntity<List<GetPostDTO>> postsPorNick(@RequestParam("nick") String nick,
                                                            @AuthenticationPrincipal Usuario usuario){

        List<GetPostDTO> getPostDTOList = postService.findAllPostOfUser(usuario, nick)
                                                     .stream()
                                                     .map(postDTOConverter::covertToPostDTO)
                                                     .collect(Collectors.toList());

        return ResponseEntity.ok().body(getPostDTOList);
    }

}
