package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyDeleteUser implements IPreCommand {
    @Autowired
    UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof UserDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado UserDTORequest");
        }

        UserDTORequest userRequest = (UserDTORequest) entity;

        User user = userValidator.toEntity(userRequest);

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
