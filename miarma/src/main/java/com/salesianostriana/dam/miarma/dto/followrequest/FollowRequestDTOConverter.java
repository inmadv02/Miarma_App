package com.salesianostriana.dam.miarma.dto.followrequest;

import com.salesianostriana.dam.miarma.model.FollowRequest;
import org.springframework.stereotype.Component;

@Component
public class FollowRequestDTOConverter {

    public FollowRequest convertToFollowRequest(CreateFollowRequest createFollowRequest){
        return FollowRequest
                    .builder()
                    .mensaje(createFollowRequest.getMensaje())
                    .build();
    }

    public GetFollowRequest convertToFollowRequestDTO(FollowRequest followRequest){
        return GetFollowRequest
                    .builder()
                    .mensaje(followRequest.getMensaje())
                    .build();
    }
}
