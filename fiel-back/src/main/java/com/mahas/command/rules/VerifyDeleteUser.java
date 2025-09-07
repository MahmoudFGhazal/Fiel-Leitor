package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.user.User;

@Component
public class VerifyDeleteUser implements ICommand {
    @Autowired
    UserValidator userValidator;

    @Override
    public String execute(FacadeRequest request) {
        User user = (User) request.getEntity();

        String error;

        // Verificar se usuario existe
        error = userValidator.userExists(user.getId());
        if (error != null) {
            return error;
        }

        return null;
    }
}
