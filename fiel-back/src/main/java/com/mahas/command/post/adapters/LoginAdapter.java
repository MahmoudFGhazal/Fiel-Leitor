package com.mahas.command.post.adapters;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.User;
import com.mahas.dto.response.user.UserDTOResponse;

@Component
public class LoginAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        FacadeResponse response = new FacadeResponse();
        DomainEntity entity = sqlResponse.getEntity();
        
        if (!(entity instanceof User)) {
            return response;
        }

        User user = (User) entity;
        
        UserDTOResponse userResponse = new UserDTOResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setActive(user.getActive());

        DataResponse data = new DataResponse();
        data.setEntity(userResponse);

        response.setData(data);

        return response;
    }
}
