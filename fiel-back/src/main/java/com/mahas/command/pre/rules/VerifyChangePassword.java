package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.PasswordValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyChangePassword implements IPreCommand {
    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        UserDTORequest userRequest = (UserDTORequest) request.getEntity();
        SQLRequest sqlRequest = new SQLRequest();

        String error;
        // Verificar formato da senha
        error = passwordValidator.isValidPasswordFormat(userRequest.getPassword());
        if (error != null) {
            throw new ValidationException(error);
        }

        if (!userValidator.userExists(userRequest.getId())) {
            throw new ValidationException("Usuario não encontrado");
        }
    
        User user = userValidator.toEntity(userRequest);

        sqlRequest.setEntity(user);

        return sqlRequest;
    }
}
