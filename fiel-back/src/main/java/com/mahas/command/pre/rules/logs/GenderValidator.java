package com.mahas.command.pre.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.facade.Facade;

@Component
public class GenderValidator {
    @Autowired
    Facade facade;

    public String validateGenderExists(Integer genderId) {
        if (genderId == null) {
            return "Id do genero não pode ser vazio";
        }

        FacadeRequest request = new FacadeRequest();

        GenderDTORequest gender = new GenderDTORequest();
        gender.setId(genderId);

        request.setEntity(gender);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        if (response.getData() == null || 
            response.getData().getEntity() == null) {
            return "Gênero não existente";
        }

        return null;
    }
}
