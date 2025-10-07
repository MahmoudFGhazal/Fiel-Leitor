package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyChangePassword implements IPreCommand {
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof UserDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado UserDTORequest");
        }

        UserDTORequest userRequest = (UserDTORequest) entity;

        communValidator.validateNotBlack(userRequest.getId() == null ? null : userRequest.getId().toString(), "Id");
        communValidator.validateNotBlack(userRequest.getPassword(), "Senha");
        communValidator.validateNotBlack(userRequest.getNewPassword(), "Nova senha");

        SQLRequest sqlRequest = new SQLRequest();
        User user = new User();

        if (!userValidator.userExists(userRequest.getId())) {
            throw new ValidationException("Usuario não encontrado");
        }

        if(!userValidator.checkPasswordCorrect(userRequest.getId(), userRequest.getPassword())) {
            throw new ValidationException("Senha Incorreta");
        }

        // Verificar formato da senha
        userValidator.isValidPasswordFormat(userRequest.getNewPassword());
    
        user.setId(userRequest.getId());
        user.setPassword(userRequest.getNewPassword());

        sqlRequest.setEntity(user);

        return sqlRequest;
    }
}
