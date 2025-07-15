package com.devansh.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String m) {
        super(m);
    }

    public ResourceNotFoundException(String m, Throwable cause) {
        super(m, cause);
    }

}
