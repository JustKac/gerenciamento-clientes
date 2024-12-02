package br.com.fsbr.gerenciamento_clientes.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.fsbr.gerenciamento_clientes.exceptions.RequiredObjectIsNullException;
import br.com.fsbr.gerenciamento_clientes.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public ResponseEntity<String> handleRequiredObjectIsNullException(RequiredObjectIsNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required object is null");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}