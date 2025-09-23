package com.mahas.command.pre.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.exception.ValidationException;

@Component
public class VerifyLogin implements IPreCommand {
    @Override
    public SQLRequest execute(FacadeRequest request) {
        User user = (User) request.getEntity();

        // Validar email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email não especificado");
        }

        // Validar senha
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("Senha não especificada");
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(user);
        return sqlRequest;
    }
}
