package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.sale.BaseStatusSaleCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.request.sale.StatusSaleDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.sale.StatusSaleDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusSaleValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseStatusSaleCommand baseStatusSaleCommand;
   
    public StatusSale toEntity(StatusSaleDTORequest dto) {
        if (dto == null) return null;

        StatusSale s = new StatusSale();
        s.setId(dto.getId() != null ? dto.getId() : null);
        
        StatusSaleName name = null;

        if (dto.getName() != null) {
            for (StatusSaleName statusName : StatusSaleName.values()) {
                if (statusName.getValue().equalsIgnoreCase(dto.getName())) {
                    name = statusName;
                    break;
                }
            }

            if (name == null) {
                throw new ValidationException("StatusSale inválido: " + dto.getName());
            }
        }

        s.setName(name);

        return s;
    }

    public Integer getStatusSaleDefault() {
        String statusDefaultName = StatusSaleName.PROCESSING.getValue();

        FacadeRequest req = new FacadeRequest();

        StatusSaleDTORequest statusSaleReq = new StatusSaleDTORequest();
        statusSaleReq.setName(statusDefaultName);

        req.setEntity(statusSaleReq);
        req.setPreCommand(baseStatusSaleCommand);
        req.setLimit(1);

        FacadeResponse res = facade.query(req);

        if(res.getData().getEntity() == null) {
            throw new ValidationException("Status da venda padrão não encontrada");
        }

        DTOResponse entity = res.getData().getEntity();
        StatusSaleDTOResponse statusSaleRes = (StatusSaleDTOResponse) entity;

        return statusSaleRes.getId();
    }
}
