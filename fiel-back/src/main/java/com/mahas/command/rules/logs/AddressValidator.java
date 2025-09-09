package com.mahas.command.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.Address;
import com.mahas.facade.Facade;

@Component
public class AddressValidator {
    @Autowired
    Facade facade;
    
    public boolean addressExists(Long id) {
        Address address = new Address();
        address.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(address);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DomainEntity entity = response.getData().getEntity();
        
        if(entity != null) {
            return true;
        }
        
        return false; 
    }
}
