package br.com.fsbr.gerenciamento_clientes.exceptions;


public class RequiredObjectIsNullException extends RuntimeException {

    public RequiredObjectIsNullException(String message) {
        super(message);
    }

    public RequiredObjectIsNullException() {
        super("It is not allowed to persist a null object!");
    }
    
}
