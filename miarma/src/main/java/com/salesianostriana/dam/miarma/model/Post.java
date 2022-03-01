package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    private String titulo, descripcion, urlFichero1, urlFichero2;

    private Visibilidad visibilidad;

    @ManyToOne
    private Usuario usuarioPublicacion;

    private int likes;

    //// HELPERS ////

    public void addToUsuario(Usuario u){
        this.usuarioPublicacion = u;
        u.getPublicaciones().add(this);
    }

    public void removeFromUsuario(Usuario u){
        u.getPublicaciones().remove(this);
        this.usuarioPublicacion = null;
    }
}
