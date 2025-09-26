package com.mahas.command.post.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.User;
import com.mahas.dto.response.user.UserDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class LoginAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof User)) {
            throw new ValidationException("Tipo de entidade inválido, esperado User");
        }

        User user = (User) entity;
        
        UserDTOResponse userResponse = new UserDTOResponse();
        userResponse.setId(user.getId());
        userResponse.setActive(user.getActive());
        System.out.println(user.getActive());

        DataResponse data = new DataResponse();
        data.setEntity(userResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
