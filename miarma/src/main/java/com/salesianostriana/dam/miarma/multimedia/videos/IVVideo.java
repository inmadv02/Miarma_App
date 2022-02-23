package com.salesianostriana.dam.miarma.multimedia.videos;

import org.springframework.stereotype.Service;

@Service
public class IVVideo implements VideoScaler{

    @Override
    public byte[] scaleVideo(byte[] image) {
        return new byte[0];
    }
}
