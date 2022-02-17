package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequestId implements Serializable {

    private UUID quiereSeguir_id;

    private UUID acepta_id;
}
