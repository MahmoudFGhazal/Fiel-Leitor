package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.domain.user.User;
import com.mahas.exception.ValidationException;

@Component
public class VerifyGetUserByUser implements IPreCommand {
    @Autowired
    private UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        Address address = (Address) request.getEntity();
        User user = address.getUser();

        // Verificar se ID do usuário foi fornecido
        if (user.getId() == null) {
            throw new ValidationException("Id do usuário não especificado");
        }

        // Verificar se o usuário existe
        if (!userValidator.userExists(user.getId())) {
            throw new ValidationException("Usuário não encontrado");
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(user);
        return sqlRequest;
    }
}
