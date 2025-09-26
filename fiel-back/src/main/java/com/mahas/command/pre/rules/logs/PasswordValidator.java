package com.mahas.command.pre.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.base.user.BaseUserCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

@Component
public class PasswordValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseUserCommand baseUserCommand;

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

    public boolean checkPassword(Integer id, String password) {
        UserDTORequest user = new UserDTORequest();
        user.setId(id);
        user.setPassword(password);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null; 
    }
}
