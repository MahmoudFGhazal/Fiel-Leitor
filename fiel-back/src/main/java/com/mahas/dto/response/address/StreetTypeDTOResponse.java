package com.mahas.dto.response.address;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.address.StreetType;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeDTOResponse implements DTOResponse {
    private Integer id;
    private String streetType;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof StreetType st) {
            this.id = st.getId();
            this.streetType = st.getStreetType();
        }
    }
}
