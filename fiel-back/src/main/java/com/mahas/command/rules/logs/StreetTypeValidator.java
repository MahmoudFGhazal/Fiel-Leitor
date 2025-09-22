package com.mahas.command.rules.logs;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.address.StreetTypeDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreetTypeValidator {
    @Autowired
    Facade facade;

    public boolean streetTypeExists(Integer streetTypeId) {
        StreetTypeDTORequest streetType = new StreetTypeDTORequest();
        streetType.setId(streetTypeId);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(streetType);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }
}
