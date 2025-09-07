package com.mahas.command.rules.logs;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberValidator {
    public String isValidPhoneNumberFormat(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "Número de telefone não pode ser vazio";
        }

        if (phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            return "Número de telefone inválido";
        }
        
        return null; 
    }
}
