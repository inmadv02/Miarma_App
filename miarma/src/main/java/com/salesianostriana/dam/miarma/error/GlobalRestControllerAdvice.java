package com.salesianostriana.dam.miarma.error;

import com.salesianostriana.dam.miarma.error.model.ApiError;
import com.salesianostriana.dam.miarma.error.model.ApiSubError;
import com.salesianostriana.dam.miarma.error.model.ApiValidationSubError;
import com.salesianostriana.dam.miarma.error.tiposErrores.EntityNotFoundException;
import com.salesianostriana.dam.miarma.error.tiposErrores.ListEntityNotFoundException;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    private ResponseEntity<Object> buildApiError400(Exception ex, WebRequest request) {
        return buildApiError(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<Object> buildApiError(HttpStatus status, Exception exception, WebRequest webRequest) {
        return ResponseEntity
                .status(status)
                .body(new ApiError(status, exception.getMessage(),
                        ((ServletWebRequest) webRequest).getRequest().getRequestURI(),
                        LocalDateTime.now()));
    }

    private ResponseEntity<Object> buildApiErrorWithSubError(HttpStatus estado, String mensaje, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(estado)
                .body(new ApiError(estado, mensaje, ((ServletWebRequest) request).getRequest().getRequestURI(), LocalDateTime.now(), subErrores));

    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(ListEntityNotFoundException.class)
    public ResponseEntity<?> handleListEntityNotFoundException(ListEntityNotFoundException exception, WebRequest webRequest) {
        return buildApiError(HttpStatus.NOT_FOUND, exception, webRequest);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request){
        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "Errores en la validación",
                request, exception.getConstraintViolations()
                        .stream()
                        .map(c -> ApiValidationSubError.builder()
                                .objeto(c.getRootBeanClass().getSimpleName())
                                .campo(((PathImpl) c.getPropertyPath()).getLeafNode().asString())
                                .valorRechazado(c.getInvalidValue())
                                .mensaje(c.getMessage())
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {


        List<ApiSubError> subErroresList = new ArrayList<>();

        exception.getAllErrors().forEach(error -> {

            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                subErroresList.add(
                        ApiValidationSubError.builder()
                                .objeto(fieldError.getObjectName())
                                .campo(fieldError.getField())
                                .valorRechazado(fieldError.getRejectedValue())
                                .mensaje(fieldError.getDefaultMessage())
                                .build()
                );
            } else {
                ObjectError objectError = (ObjectError) error;
                subErroresList.add(ApiValidationSubError.builder()
                        .objeto(objectError.getObjectName())
                        .mensaje(objectError.getDefaultMessage())
                        .build()
                );
            }
        });

        return buildApiErrorWithSubError(
                HttpStatus.BAD_REQUEST,
                "Errores en la validación",
                request,
                subErroresList.isEmpty() ? null : subErroresList
        );

    }

}
