package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.dto.followrequest.CreateFollowRequest;
import com.salesianostriana.dam.miarma.dto.followrequest.FollowRequestDTOConverter;
import com.salesianostriana.dam.miarma.model.FollowRequest;
import com.salesianostriana.dam.miarma.repository.FollowRequestRepository;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowRequestService {

    private final FollowRequestRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final FollowRequestDTOConverter dtoConverter;


    public FollowRequest follow(CreateFollowRequest createFollowRequest, Usuario usuario, String nickname){

        Optional<Usuario> queremosSeguir = usuarioRepository.findFirstByNickname(nickname);

        usuario = usuarioRepository.findFirstByNickname(usuario.getNickname()).get();

        FollowRequest followRequest = dtoConverter.convertToFollowRequest(createFollowRequest);

        followRequest.addToUsuario(queremosSeguir.get());
        followRequest.addToUsuario2(usuario);

        repository.save(followRequest);
        usuarioRepository.save(usuario);

        return followRequest;
    }
}
