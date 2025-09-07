package com.mahas.command.rules.logs;

public class PhoneNumberValidator {
    public String isValidPhoneNumberFormat(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "Número de telefone não pode ser vazio";
        }

        if (phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            return "Número de telefone inválido";
        }

        if (phoneNumber.length() == 13) {
            return "Número de telefone inválido: código do país incorreto";
        }

        return null; 
    }
}
