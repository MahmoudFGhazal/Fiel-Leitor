package com.mahas.exception;

import com.mahas.domain.TypeResponse;

public class ValidationException extends RuntimeException { 
    private final TypeResponse typeResponse;

    public ValidationException(String message) { 
        super(message); 
        this.typeResponse = TypeResponse.OK;
    } 

    public ValidationException(String message, TypeResponse typeResponse) {
        super(message);
        this.typeResponse = typeResponse;
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }
}
