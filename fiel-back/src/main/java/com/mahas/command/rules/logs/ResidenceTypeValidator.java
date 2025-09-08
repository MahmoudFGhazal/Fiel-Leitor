package com.mahas.command.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.ResidenceType;
import com.mahas.facade.Facade;

@Component
public class ResidenceTypeValidator {
    @Autowired
    Facade facade;

    public boolean residenceTypeExists(Long streetTypeId) {
        ResidenceType residenceType = new ResidenceType();
        residenceType.setId(streetTypeId);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(residenceType);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DomainEntity entity = response.getData().getEntity();

        if(entity != null) {
            return true;
        }

        return false;
    }
}
