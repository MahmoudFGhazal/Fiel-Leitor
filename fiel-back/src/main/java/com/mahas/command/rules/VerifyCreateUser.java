package com.mahas.command.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.UserEmail;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.user.User;

@Component
public class VerifyCreateUser implements ICommand {
    @Override
    public String execute(FacadeRequest request) {
        User user = (User) request.getEntity();
        UserEmail userEmail = new UserEmail();
        String error;

        // Validar formato do email
        error = userEmail.isValidEmailUserFormat(user.getEmail());
        if (error != null) {
            return error;
        }

        // Verificar se email já existe
        error = userEmail.emailExists(user.getEmail());
        if (error != null) {
            return error;
        }

        return null;
    }
}
