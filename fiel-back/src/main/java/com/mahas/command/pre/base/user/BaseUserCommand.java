package com.mahas.command.pre.base.user;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseUserCommand implements IPreCommand {
    @Autowired
    @Lazy
    UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof UserDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado UserDTORequest");
        }

        UserDTORequest userRequest = (UserDTORequest) entity;
        System.out.println(userRequest.getId());
        User user = userValidator.toEntity(userRequest);
        System.out.println(user.getId());
        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(user);

        if(request.getLimit() != null && request.getLimit() > 0) {
            sqlRequest.setLimit(request.getLimit());
            if(request.getPage() != null && request.getPage() > 0) {
                sqlRequest.setPage(request.getPage());
            }else {
                sqlRequest.setPage(1);
            }
        }else {
            sqlRequest.setLimit(0);
            sqlRequest.setPage(1);
        }
        
        return sqlRequest;
    }
}
