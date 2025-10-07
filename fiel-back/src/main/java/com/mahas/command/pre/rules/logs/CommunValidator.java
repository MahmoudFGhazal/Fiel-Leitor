package com.mahas.command.pre.rules.logs;

import com.mahas.exception.ValidationException;

import org.springframework.stereotype.Component;

@Component
public class CommunValidator {
    public String toNumeric(String str) {
        String digits = str.replaceAll("\\D", "");

        return digits.isEmpty() ? null : digits;
    }

    public void validateNotBlack(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldName + " não especificado");
        }
    }
}
