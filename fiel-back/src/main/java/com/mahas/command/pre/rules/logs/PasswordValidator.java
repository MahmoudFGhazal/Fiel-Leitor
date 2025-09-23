package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public String isValidPasswordFormat(String password) {
        if (password == null || password.isBlank()) {
            return "Senha não pode ser vazia";
        }

        if (password.length() < 8) {
            return "Senha deve conter pelo menos 8 caracteres";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "Senha deve conter pelo menos uma letra maiúscula";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Senha deve conter pelo menos uma letra minúscula";
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return "Senha deve conter pelo menos um caractere especial";
        }

        return null;
    }
}
