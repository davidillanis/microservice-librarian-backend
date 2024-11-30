package org.microservice.users.utils.exception;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException(String data) {
        super("El dato ".concat(data).concat(" ya existe"));
    }
}
