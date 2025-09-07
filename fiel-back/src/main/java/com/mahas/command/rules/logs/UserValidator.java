package com.mahas.command.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.User;
import com.mahas.facade.Facade;

@Component
public class UserValidator {
    @Autowired
    Facade facade;
    
    public String userExists(Long id) {
        if (id == null) {
            return "Id do usuario não pode ser vazio";
        }

        FacadeRequest request = new FacadeRequest();

        User user = new User();
        request.setEntity(user);

        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DomainEntity entity = response.getData().getEntity();

        if(entity == null) {
            return "Usuario não encontrado";
        }
        
        return null; 
    }
}
