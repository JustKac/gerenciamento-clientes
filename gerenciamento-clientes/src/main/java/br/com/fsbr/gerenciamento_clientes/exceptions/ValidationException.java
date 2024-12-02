package br.com.fsbr.gerenciamento_clientes.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
