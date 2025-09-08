package com.mahas.command.rules.logs;

import org.springframework.stereotype.Component;

@Component
public class ZIPValidator {
    public String isValidZIPFormat(String zip) {
        if (zip == null || zip.isBlank()) {
            return "CEP não pode ser vazio";
        }

        String onlyDigits = zip.replaceAll("\\D", "");

        if (onlyDigits.length() != 8) {
            return "CEP deve conter 8 dígitos";
        }

        return null;
    }
}
