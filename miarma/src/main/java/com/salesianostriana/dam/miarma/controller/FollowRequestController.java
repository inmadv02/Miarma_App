package com.salesianostriana.dam.miarma.controller;

import com.salesianostriana.dam.miarma.dto.followrequest.CreateFollowRequest;
import com.salesianostriana.dam.miarma.dto.followrequest.FollowRequestDTOConverter;
import com.salesianostriana.dam.miarma.dto.followrequest.GetFollowRequest;
import com.salesianostriana.dam.miarma.model.FollowRequest;
import com.salesianostriana.dam.miarma.services.FollowRequestService;
import com.salesianostriana.dam.miarma.users.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowRequestController {

    private final FollowRequestService followRequestService;
    private final FollowRequestDTOConverter dtoConverter;

    @PostMapping("/{nick}")
    public ResponseEntity<GetFollowRequest> followUser(@PathVariable String nick,
                                                       @RequestBody CreateFollowRequest createFollowRequest,
                                                       @AuthenticationPrincipal Usuario usuario){

        FollowRequest followRequest = followRequestService.follow(createFollowRequest, usuario, nick);

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoConverter.convertToFollowRequestDTO(followRequest));

    }
}
