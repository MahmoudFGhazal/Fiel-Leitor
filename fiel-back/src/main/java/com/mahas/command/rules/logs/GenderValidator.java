package com.mahas.command.rules.logs;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Gender;
import com.mahas.facade.Facade;

public class GenderValidator {
    public String validateGenderExists(Long genderId) {
        if (genderId == null) {
            return "Id do genero não pode ser vazio";
        }

        Facade facade = new Facade();
        FacadeRequest request = new FacadeRequest();

        Gender gender = new Gender();
        gender.setId(genderId);

        request.setEntity(gender);

        FacadeResponse response = facade.query(request);

        if (response.getData() == null || 
            response.getData().getEntities() == null || 
            response.getData().getEntities().isEmpty()) {
            return "Gênero não existente";
        }

        return null;
    }
}
