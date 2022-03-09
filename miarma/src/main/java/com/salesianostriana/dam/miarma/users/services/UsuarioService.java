package com.salesianostriana.dam.miarma.users.services;

import com.salesianostriana.dam.miarma.error.tiposErrores.UserNotFoundException;
import com.salesianostriana.dam.miarma.model.Post;
import com.salesianostriana.dam.miarma.multimedia.images.ImageScaler;
import com.salesianostriana.dam.miarma.multimedia.videos.VideoScaler;
import com.salesianostriana.dam.miarma.services.PostService;
import com.salesianostriana.dam.miarma.services.StorageService;
import com.salesianostriana.dam.miarma.services.base.BaseService;
import com.salesianostriana.dam.miarma.users.dto.*;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final StorageService storageService;
    private final UserDTOConverter dtoConverter;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return this.repositorio.findFirstByNickname(nickname)
                .orElseThrow(()-> new UsernameNotFoundException(nickname + " no encontrado"));
    }

    public Usuario save(CreateUsuarioDto nuevoUsuario, MultipartFile file) throws IOException, ImageException, VideoException {

        List<String> uris = new ArrayList<>(postService.uploadFiles(file));

        Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoUsuario.getPassword()))
                    .fechaNacimiento(nuevoUsuario.getFechaNacimiento())
                    .foto(uris.get(0))
                    .idAdmin(true)
                    .fullname(nuevoUsuario.getFullname())
                    .nickname(nuevoUsuario.getNickname())
                    .email(nuevoUsuario.getEmail())
                    .visibilidad(nuevoUsuario.getVisibilidad())
                    .build();
            return save(usuario);

    }

    public Page<Post> myPosts(Usuario usuario, Pageable pageable){

        usuario = repositorio.findFirstByNickname(usuario.getNickname()).get();

        Page<Post> lista = new PageImpl<>(usuario.getPublicaciones());

        return lista;
    }


    public Usuario editProfile(Usuario usuario, GetUsuarioMoreDetailsDTO usuarioDto, MultipartFile file) throws IOException, VideoException {

        storageService.deleteFile(usuario.getFoto());
        String uri = postService.uploadFiles(file).get(0);

        usuario.setNickname(usuarioDto.getNickname());
        usuario.setFullname(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setBiografia(usuarioDto.getDescripcion());
        usuario.setFechaNacimiento(usuarioDto.getFechaNacimiento());
        usuario.setFoto(uri);
        usuario.setVisibilidad(usuarioDto.getVisibilidad());
        usuario.setPassword(usuarioDto.getPassword());

        passwordEncoder.encode(usuarioDto.getPassword());

        repositorio.save(usuario);

        return usuario;

    }

    public GetUsuarioWithPostsImagesDto myProfile(Usuario usuario){
        Usuario usuario1 = repositorio.findFirstByNickname(usuario.getNickname()).get();
        GetUsuarioWithPostsImagesDto usuarioDto = dtoConverter.convertToGetUsuarioWithPhotos(usuario);

        return usuarioDto;
    }

    public Page<Usuario> allUsers(Usuario usuario, Pageable pageable){
        return repositorio.findAll(pageable);
    }

    public GetUsuarioAdminDto beAdmin(UUID id, Usuario usuario){

        if(usuario.isIdAdmin()){
            Usuario usuario1 = repositorio.findById(id).get();

            usuario1.setIdAdmin(true);
            repositorio.save(usuario1);

            GetUsuarioAdminDto getUsuarioAdminDto = dtoConverter.convertUserEntityToGetUserAdminDto(usuario1);
            return getUsuarioAdminDto;
        }

        else {
            throw  new UserNotFoundException(id, Usuario.class);
        }


    }

    public GetUsuarioAdminDto notAdmin(UUID id, Usuario usuario){

        if(usuario.isIdAdmin()){
            Optional<Usuario> usuario1 = repositorio.findById(id);

            usuario1.get().setIdAdmin(false);

            GetUsuarioAdminDto getUsuarioAdminDto = dtoConverter.convertUserEntityToGetUserAdminDto(usuario1.get());

            return getUsuarioAdminDto;
        }

        else {
            throw  new UserNotFoundException(id, Usuario.class);
        }

    }


}
