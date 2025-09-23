package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.address.BaseStreetTypeCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.StreetType;
import com.mahas.dto.request.address.StreetTypeDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreetTypeValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseStreetTypeCommand baseStreetTypeCommand;

    public StreetType toEntity(StreetTypeDTORequest dto) {
        if (dto == null) return null;

        StreetType streetType = new StreetType();
        streetType.setId(dto.getId());
        streetType.setStreetType(dto.getStreetType());

        return streetType;
    }

    public boolean streetTypeExists(Integer streetTypeId) {
        StreetTypeDTORequest streetType = new StreetTypeDTORequest();
        streetType.setId(streetTypeId);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(streetType);
        request.setLimit(1);
        request.setPreCommand(baseStreetTypeCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }
}
