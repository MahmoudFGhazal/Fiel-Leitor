package com.mahas.command.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.StreetType;
import com.mahas.facade.Facade;

@Component
public class StreetTypeValidator {
    @Autowired
    Facade facade;

    public boolean streetTypeExists(Long streetTypeId) {
        StreetType streetType = new StreetType();
        streetType.setId(streetTypeId);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(streetType);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DomainEntity entity = response.getData().getEntity();

        if(entity != null) {
            return true;
        }

        return false;
    }
}
