package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.dto.post.CreatePostDTO;
import com.salesianostriana.dam.miarma.dto.post.GetPostDTO;
import com.salesianostriana.dam.miarma.dto.post.PostDTOConverter;
import com.salesianostriana.dam.miarma.error.tiposErrores.InvalidFormatException;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
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

    public List<String> uploadFiles(MultipartFile file, int size) throws IOException, VideoException {

        if(Objects.equals(file.getContentType(), "video")){

            String videoEscalado = storageService.scaleVideo(file);

            String uriVEscalado = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("uploads/")
                    .path(videoEscalado)
                    .toUriString();

            String videoNormal = storageService.store(file);

            String uriV = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("uploads/")
                    .path(videoNormal)
                    .toUriString();

            return Arrays.asList(uriVEscalado, uriV);
        }

        if (Objects.equals(file.getContentType(), "image")) {
            String imagenEscalada = storageService.scaleImage(file, size);

            String uriEscalada = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("uploads/")
                    .path(imagenEscalada)
                    .toUriString();

            String fileName = storageService.store(file);

            String uriOriginal = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("uploads/")
                    .path(fileName)
                    .toUriString();

            return Arrays.asList(uriEscalada, uriOriginal);
        }

        else {
            throw new InvalidFormatException("El archivo que ha subido no tiene un formato correcto");
        }
    }

    public Post addPost (CreatePostDTO postDTO, MultipartFile file, Usuario usuario) throws IOException, VideoException {

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();

        String uri = uploadFiles(file, 1024).get(0);
        String uri2 = uploadFiles(file, 1024).get(1);

        postDTO.setUrlFoto(uri);
        postDTO.setUrlFoto2(uri2);

        Post post = postDTOConverter.convertToPost(postDTO);
        post.addToUsuario(usuario);
        postRepository.save(post);

        return post;
    }

    public Post editPost(GetPostDTO postDTO, Long id, MultipartFile file, Usuario usuario) throws IOException, VideoException {

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();
        String uri = uploadFiles(file, 1024).get(0);
        String uriImagenNormal = uploadFiles(file, 1024).get(1);
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()){
            throw new EntityNotFoundException(id, Post.class);
        }
        else if(!usuario.equals(postOptional.get().getUsuarioPublicacion())){
            throw new FileNotFoundException("No se ha podido encontrar el fichero");
        }

        else {
            return postOptional.map(post -> {
                post.setTitulo(postDTO.getTitulo());
                post.setDescripcion(postDTO.getTexto());
                storageService.deleteFile(post.getUrlFichero1());
                storageService.deleteFile(post.getUrlFichero2());
                post.setUrlFichero1(uri);
                post.setUrlFichero2(uriImagenNormal);
                post.setVisibilidad(postDTO.getVisibilidad());
                postRepository.save(post);
                return post;
            }).get();
        }
    }


    public List<Post> findAllPublicPost(){
        return postRepository.findByVisibilidad(Visibilidad.PUBLIC);
    }

    public void deletePost(Long id, Usuario usuario) throws FileNotFoundException {

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();
        Optional<Post> postAEliminar = postRepository.findById(id);

        if(postAEliminar.isPresent()) {
            storageService.deleteFile(postAEliminar.get().getUrlFichero1());
            storageService.deleteFile(postAEliminar.get().getUrlFichero2());
            postRepository.deleteById(id);
        }

        else if(!usuario.equals(postAEliminar.get().getUsuarioPublicacion())){
            throw new FileNotFoundException("No se ha podido encontrar el fichero");
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
