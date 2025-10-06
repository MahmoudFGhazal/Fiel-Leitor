package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.domain.sale.StatusSale;
import com.mahas.dto.request.sale.StatusSaleDTORequest;

@Component
public class StatusSaleValidator {
    public StatusSale toEntity(StatusSaleDTORequest dto) {
        if (dto == null) return null;

        StatusSale s = new StatusSale();
        s.setId(dto.getId() != null ? dto.getId().intValue() : null);
        s.setName(dto.getName());

        return s;
    }
}
