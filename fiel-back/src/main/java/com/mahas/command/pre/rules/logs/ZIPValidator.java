package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.exception.ValidationException;

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

    public String convertZIP(String zip) {
        if (zip == null) {
            throw new ValidationException("CEP não pode ser nulo");
        }

        String digits = zip.replaceAll("\\D", "");

        if (digits.length() != 8) {
            throw new ValidationException("CEP inválido, deve conter 8 dígitos");
        }

        return digits.substring(0, 5) + "-" + digits.substring(5);
    }
}
