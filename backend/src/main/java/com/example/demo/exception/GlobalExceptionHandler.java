package com.example.demo.exception;

import com.example.demo.dto.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");

        log.warn("Error de validación: {}", message);

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Bad Request");
        error.setMessage(message);
        error.setPath(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        log.warn("Error de validación: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Bad Request");
        error.setMessage(ex.getMessage());
        error.setPath(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.client.HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(
            org.springframework.web.client.HttpServerErrorException.InternalServerError ex, WebRequest request) {

        log.error("Error del servicio ML: {}", ex.getResponseBodyAsString());

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Bad Request");
        
        // Extraer el mensaje específico del ML Service si está disponible
        String message = ex.getResponseBodyAsString();
        if (message.contains("No se pueden escribir solo números")) {
            error.setMessage("No se pueden escribir solo números. El texto debe contener al menos una letra.");
        } else {
            error.setMessage("Error en el procesamiento del texto: " + message);
        }
        
        error.setPath(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpServerErrorException(
            HttpServerErrorException ex, WebRequest request) {

        log.error("Error del servicio ML: {}", ex.getResponseBodyAsString());

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Internal Server Error");
        
        // Extraer el mensaje específico del ML Service si está disponible
        String message = ex.getResponseBodyAsString();

        if (message != null && message.contains("No se pueden escribir solo números")) {
            error.setMessage("No se pueden escribir solo números. El texto debe contener al menos una letra.");
        } else {
            error.setMessage("Error en el procesamiento del texto.");
        }

        error.setPath(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        log.error("Error en tiempo de ejecución: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Internal Server Error");
        error.setMessage(ex.getMessage());
        error.setPath(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        log.error("Error no manejado: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Internal Server Error");
        error.setMessage("Ocurrió un error inesperado");
        error.setPath(request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}