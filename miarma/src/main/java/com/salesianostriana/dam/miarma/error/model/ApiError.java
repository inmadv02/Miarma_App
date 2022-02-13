package com.salesianostriana.dam.miarma.error.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    private HttpStatus estado;
    private int codigo;
    private String mensaje;
    private String ruta;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrores;

    public ApiError(HttpStatus estado, String mensaje, String ruta, LocalDateTime fecha) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.fecha = fecha;
    }

    public ApiError(HttpStatus estado, String mensaje, String requestURI, List<ApiSubError> subErrores) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.ruta = requestURI;
        this.subErrores = subErrores;
    }


    public ApiError(HttpStatus estado, String mensaje, String requestURI, LocalDateTime now, List<ApiSubError> subErrores) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.ruta = requestURI;
        this.fecha = now;
        this.subErrores = subErrores;
    }
}
