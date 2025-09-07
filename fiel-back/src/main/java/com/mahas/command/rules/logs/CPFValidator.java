package com.mahas.command.rules.logs;

public class CPFValidator {
    public String isValidCPFFormat(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return "CPF não pode ser vazio";
        }

        if (cpf.length() != 11) {
            return "CPF deve conter 11 dígitos";
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return "CPF inválido: todos os dígitos são iguais";
        }

        if (!isValidCPF(cpf)) {
            return "CPF inválido";
        }

        return null; 
    }

    private boolean isValidCPF(String cpf) {
        try {
            int sum = 0;
            int weight = 10;

            for (int i = 0; i < 9; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                sum += num * weight--;
            }

            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            if (firstDigit != Integer.parseInt(cpf.substring(9, 10))) {
                return false;
            }

            sum = 0;
            weight = 11;
            for (int i = 0; i < 10; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                sum += num * weight--;
            }

            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            return secondDigit == Integer.parseInt(cpf.substring(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
