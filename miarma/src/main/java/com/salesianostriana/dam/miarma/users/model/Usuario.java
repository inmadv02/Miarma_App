package com.salesianostriana.dam.miarma.users.model;

import com.salesianostriana.dam.miarma.model.Post;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String fullname, nickname, foto, biografia;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Visibilidad visibilidad;

    @ManyToMany(mappedBy = "seguidores", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Usuario> siguiendo = new ArrayList<>();;

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Usuario> seguidores = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Usuario> quieroSeguirte = new ArrayList<>(); //Son los usuarios que quieren que les acepte el follow

    @ManyToMany(mappedBy = "quieroSeguirte", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Usuario> meQuierenSeguir = new ArrayList<>(); //Usuarios que me tienen que aceptar el follow

    @OneToMany(mappedBy = "usuarioPublicacion")
    @Builder.Default
    private List<Post> publicaciones = new ArrayList<>();

    private LocalDate fechaNacimiento;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("usuarioRegistrado"));
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    ////// HELPERS ////


}
