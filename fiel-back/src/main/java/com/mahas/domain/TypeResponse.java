package com.mahas.domain;

public enum TypeResponse {
    OK(200),
    CLIENT_ERROR(400),
    CONFLICT(409),
    SERVER_ERROR(500);

    private final int code;

    TypeResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}