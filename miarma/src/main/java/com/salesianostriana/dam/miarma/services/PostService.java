package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.dto.post.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.post.GetPostDTO;
import com.salesianostriana.dam.miarma.dto.post.PostDTOConverter;
import com.salesianostriana.dam.miarma.error.tiposErrores.FileNotFoundException;
import com.salesianostriana.dam.miarma.error.tiposErrores.UserNotFoundException;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.error.tiposErrores.EntityNotFoundException;
import com.salesianostriana.dam.miarma.repository.PostRepository;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long, PostRepository> {

    private final PostRepository postRepository;
    private final PostDTOConverter postDTOConverter;
    private final StorageService storageService;
    private final UsuarioRepository usuarioRepository;

    public String uploadFiles(MultipartFile file) throws IOException, VideoException {

        if(Objects.equals(file.getContentType(), "video/mp4")){
            String videoEscalado = storageService.scaleVideo(file);
            String videoNormal = storageService.store(file);

            String uriV = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(videoNormal)
                    .toUriString();
            return uriV;
        }

        String imagenEscalada = storageService.scaleImage(file, 1024);
        String fileName = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(imagenEscalada)
                .toUriString();

        String uriOriginal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();

        return uri;
    }

    public Post addPost (CreatePostDTO postDTO, MultipartFile file, Usuario usuario) throws IOException, VideoException {

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();

        String uri = uploadFiles(file);
        postDTO.setUrlFoto(uri);

        Post post = postDTOConverter.convertToPost(postDTO);
        post.addToUsuario(usuario);
        postRepository.save(post);

        return post;
    }

    public Post editPost(GetPostDTO postDTO, Long id, MultipartFile file, Usuario usuario) throws IOException, VideoException {

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();
        String uri = uploadFiles(file);
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()){
            throw new EntityNotFoundException(id, Post.class);
        }

        return postOptional.map( post -> {
                post.setTitulo(postDTO.getTitulo());
                post.setDescripcion(postDTO.getTexto());
                storageService.deleteFile(post.getUrlFichero());
                post.setUrlFichero(uri);
                post.setVisibilidad(postDTO.getVisibilidad());
                postRepository.save(post);
                return post;
            }).get();

    }


    public List<Post> findAllPublicPost(){
        return postRepository.findByVisibilidad(Visibilidad.PUBLIC);
    }

    public void deletePost(Long id, Usuario usuario){

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();
        Optional<Post> postAEliminar = postRepository.findById(id);

        if(postAEliminar.isPresent()) {
            storageService.deleteFile(postAEliminar.get().getUrlFichero());
            postRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException(id, Post.class);
        }

    }

    public Post findOne(Long id, Usuario usuario){

        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            if (post.get().getVisibilidad().getTexto().equals("Público") ||
                    usuario.getNickname().equals(post.get().getUsuarioPublicacion().getNickname())) {
                return post.get();
            }
        }
        else {
            throw new EntityNotFoundException(id, Post.class);
        }

        return null;
    }

    public List<Post> findAllPostOfUser(Usuario usuario, String nick){

        Optional<Usuario> buscado = usuarioRepository.findFirstByNickname(nick);
        List<Usuario> listaSiguiendo = usuarioRepository.publicacionesUsuario(usuario);

        if(buscado.isPresent()){
            if(listaSiguiendo.contains(buscado.get())){
                return buscado.get().getPublicaciones();
            }
            else {
                return postRepository.findByUsuarioPublicacionAndVisibilidad(buscado.get(), Visibilidad.PUBLIC);
            }
        }
        else {
            throw new UserNotFoundException(buscado.get().getId(), Usuario.class);
        }

    }




}
