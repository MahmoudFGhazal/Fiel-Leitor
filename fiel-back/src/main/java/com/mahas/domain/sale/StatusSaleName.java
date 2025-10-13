package com.mahas.domain.sale;

public enum StatusSaleName {
    PROCESSING("PROCESSING"), 
    APPROVED("APPROVED"), 
    DECLINED("DECLINED"), 
    IN_TRANSIT("IN_TRANSIT"),
    DELIVERED("DELIVERED"), 
    EXCHANGE_REQUESTED("EXCHANGE_REQUESTED"), 
    EXCHANGE_AUTHORIZED("EXCHANGE_AUTHORIZED"), 
    EXCHANGED("EXCHANGED");

    private final String value;

    StatusSaleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}