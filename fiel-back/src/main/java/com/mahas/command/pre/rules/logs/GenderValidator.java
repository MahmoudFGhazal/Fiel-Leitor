package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.user.BaseGenderCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Gender;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenderValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseGenderCommand baseGenderCommand;

    public Gender toEntity(GenderDTORequest dto) {
        if (dto == null) return null;

        Gender gender = new Gender();
        gender.setId(dto.getId());
        gender.setGender(dto.getGender());

        return gender;
    }

    public boolean genderExists(Integer id) {
        FacadeRequest request = new FacadeRequest();

        GenderDTORequest gender = new GenderDTORequest();
        gender.setId(id);

        request.setEntity(gender);
        request.setLimit(1);
        request.setPreCommand(baseGenderCommand);

        FacadeResponse response = facade.query(request);

        return !(response.getData() == null || response.getData().getEntity() == null);
    }
}
