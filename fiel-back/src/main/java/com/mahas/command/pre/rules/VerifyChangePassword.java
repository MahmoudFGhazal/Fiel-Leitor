package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.PasswordValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyChangePassword implements IPreCommand {
    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof UserDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado UserDTORequest");
        }

        UserDTORequest userRequest = (UserDTORequest) entity;

        SQLRequest sqlRequest = new SQLRequest();

        String error;
        if (!userValidator.userExists(userRequest.getId())) {
            throw new ValidationException("Usuario não encontrado");
        }

        if(!passwordValidator.checkPassword(userRequest.getId(), userRequest.getPassword())) {
            throw new ValidationException("Senha Incorreta");
        }

        // Verificar formato da senha
        error = passwordValidator.isValidPasswordFormat(userRequest.getNewPassword());
        if (error != null) {
            throw new ValidationException(error);
        }
    
        User user = userValidator.toEntity(userRequest);

        sqlRequest.setEntity(user);

        return sqlRequest;
    }
}
