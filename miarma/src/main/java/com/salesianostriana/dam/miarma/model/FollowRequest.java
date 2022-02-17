package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FollowRequest {

    @Builder.Default
    @EmbeddedId
    private FollowRequestId id = new FollowRequestId();

    @ManyToOne
    @MapsId("quiereSeguir_id")
    @JoinColumn(name="quiereSeguir_id")
    private Usuario quiereSeguir;

    @ManyToOne
    @MapsId("acepta_id")
    @JoinColumn(name="acepta_id")
    private Usuario acepta;


    private String mensaje;


    ///// HELPERS /////

    public void addToUsuario(Usuario usuario){
        this.acepta = usuario;
        usuario.getFollowRequests().add(this);
    }

    public void addToUsuario2(Usuario usuario){
        this.quiereSeguir = usuario;
        usuario.getFollowRequests().add(this);
    }



}
