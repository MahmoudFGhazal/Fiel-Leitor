package com.mahas.command.pre.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.address.ResidenceTypeDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

@Component
public class ResidenceTypeValidator {
    @Autowired
    Facade facade;

    public boolean residenceTypeExists(Integer streetTypeId) {
        ResidenceTypeDTORequest residenceType = new ResidenceTypeDTORequest();
        residenceType.setId(streetTypeId);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(residenceType);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }
}
