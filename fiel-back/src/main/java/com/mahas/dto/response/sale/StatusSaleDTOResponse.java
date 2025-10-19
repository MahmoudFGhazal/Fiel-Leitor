package com.mahas.dto.response.sale;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.sale.StatusSale;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusSaleDTOResponse implements DTOResponse {
    private Integer id;
    private String status;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof StatusSale s) {
            this.id = s.getId();
            this.status = s.getStatus() != null ? s.getStatus().getValue() : null;
        }
    }
}