package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.dto.post.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.post.GetPostDTO;
import com.salesianostriana.dam.miarma.dto.post.PostDTOConverter;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.error.tiposErrores.EntityNotFoundException;
import com.salesianostriana.dam.miarma.repository.PostRepository;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long, PostRepository> {

    private final PostRepository postRepository;
    private final PostDTOConverter postDTOConverter;
    private final StorageService storageService;
    private final UsuarioRepository usuarioRepository;

    public Post addPost (CreatePostDTO postDTO, MultipartFile file, Usuario usuario) throws IOException {

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();

        storageService.scaleImage(file, 300);

        String fileName = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();

        postDTO.setUrlFoto(uri);

        Post post = postDTOConverter.convertToPost(postDTO);

        post.addToUsuario(usuario);

        postRepository.save(post);

        return post;
    }

    public Post editPost(GetPostDTO postDTO, Long id, MultipartFile file, Usuario usuario) throws IOException{

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();

        storageService.scaleImage(file, 300);

        String fileName = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();

        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()){
            throw new EntityNotFoundException(id, Post.class);
        }

        return postOptional.map( post -> {

                post.setTitulo(postDTO.getTitulo());
                post.setDescripcion(postDTO.getTexto());
                post.setUrlFichero(uri);
                post.setVisibilidad(postDTO.getVisibilidad());
                postRepository.save(post);
                return post;
            }).get();

    }


    public List<Post> findAllPublicPost(){

        return postRepository.findByVisibilidad(Visibilidad.PUBLIC.getTexto());
    }



}
