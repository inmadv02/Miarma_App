package com.salesianostriana.dam.miarma.model;

import com.salesianostriana.dam.miarma.users.model.Usuario;
import com.salesianostriana.dam.miarma.users.model.Visibilidad;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post implements Serializable {

    @Id
    @GeneratedValue
    private Long postId;

    private String titulo, descripcion, urlFichero;

    private Visibilidad visibilidad;

    @ManyToOne
    private Usuario usuarioPublicacion;
}
