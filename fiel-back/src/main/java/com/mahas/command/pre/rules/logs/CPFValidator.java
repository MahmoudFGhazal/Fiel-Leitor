package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.user.BaseUserCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CPFValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseUserCommand baseUserCommand;

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

    public boolean cpfExists(String cpf) {
        UserDTORequest user = new UserDTORequest();
        user.setCpf(cpf);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }
}
