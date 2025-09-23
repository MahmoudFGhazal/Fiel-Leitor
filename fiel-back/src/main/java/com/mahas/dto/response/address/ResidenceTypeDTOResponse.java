package com.mahas.dto.response.address;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.address.ResidenceType;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResidenceTypeDTOResponse implements DTOResponse {
    private Integer id;
    private String residenceType;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof ResidenceType rt) {
            this.id = rt.getId();
            this.residenceType = rt.getResidenceType();
        }
    }
}
