package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.users.model.Usuario;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FollowRequestId implements Serializable {

    private Long quiereSeguir_id;

    private Long acepta_id;
}
