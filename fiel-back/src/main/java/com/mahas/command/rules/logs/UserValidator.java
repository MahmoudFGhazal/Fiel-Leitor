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
    
    public boolean userExists(Long id) {
        User user = new User();
        user.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DomainEntity entity = response.getData().getEntity();
        
        if(entity != null) {
            return true;
        }
        
        return false; 
    }
}
