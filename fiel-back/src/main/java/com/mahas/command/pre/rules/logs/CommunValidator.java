package com.mahas.command.pre.rules.logs;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.mahas.exception.ValidationException;

@Component
public class CommunValidator {
    public String toNumeric(String str) {
        String digits = str.replaceAll("\\D", "");

        return digits.isEmpty() ? null : digits;
    }

    public void validateNotBlanck(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldName + " não especificado");
        }
    }

    public void validateNotBlanck(Integer value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " deve ser maior que zero");
        }
    }

    public void validateNotBlanck(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " não especificado");
        }
    }

    public void validateNotBlanck(Collection<?> value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new ValidationException(fieldName + " não pode estar vazio");
        }
    }
}
