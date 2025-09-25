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
public class VerifyLogin implements IPreCommand {
    @Autowired
    UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof UserDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado UserDTORequest");
        }

        UserDTORequest userRequest = (UserDTORequest) entity;

        // Validar email
        if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
            throw new ValidationException("Email não especificado");
        }

        // Validar senha
        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
            throw new ValidationException("Senha não especificada");
        }

        User user = userValidator.toEntity(userRequest);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(user);
        sqlRequest.setLimit(1);
        sqlRequest.setPage(1);

        return sqlRequest;
    }
}
