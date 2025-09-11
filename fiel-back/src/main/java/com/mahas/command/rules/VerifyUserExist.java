package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.user.User;

@Component
public class VerifyUserExist implements ICommand {
    @Autowired
    private UserValidator userValidator;

    @Override
    public String execute(FacadeRequest request) {
        User user = (User) request.getEntity();

        if(user.getId() == null) {
            return "Id do usuario não especificado";
        }

        // Verificar usuario existe
        if (!userValidator.userExists(user.getId())) {
            return "Usuario não encontrado";
        }

        return null;
    }
}
