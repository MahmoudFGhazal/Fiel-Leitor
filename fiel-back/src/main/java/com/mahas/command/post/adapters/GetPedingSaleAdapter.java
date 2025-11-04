package com.mahas.command.post.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.sale.SaleDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class GetPedingSaleAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        List<DomainEntity> entities = sqlResponse.getEntities();
        
        List<DTOResponse> dtoList = new ArrayList<>(entities.size());

        for (DomainEntity e : entities) {
            if (!(e instanceof Sale)) {
                throw new ValidationException("Tipo de entidade inválido, esperado Sale");
            }

            SaleDTOResponse saleResponse = new SaleDTOResponse();
            saleResponse.mapFromEntity(e);

            if(
                (saleResponse.getStatusSale().getStatus() == null ? StatusSaleName.IN_TRANSIT.getValue() == null : saleResponse.getStatusSale().getStatus().equals(StatusSaleName.IN_TRANSIT.getValue())) ||
                (saleResponse.getStatusSale().getStatus() == null ? StatusSaleName.PROCESSING.getValue() == null : saleResponse.getStatusSale().getStatus().equals(StatusSaleName.PROCESSING.getValue())) ||
                (saleResponse.getStatusSale().getStatus() == null ? StatusSaleName.DECLINED.getValue() == null : saleResponse.getStatusSale().getStatus().equals(StatusSaleName.DECLINED.getValue())) ||
                (saleResponse.getStatusSale().getStatus() == null ? StatusSaleName.EXCHANGED.getValue() == null : saleResponse.getStatusSale().getStatus().equals(StatusSaleName.EXCHANGED.getValue())) ||
                (saleResponse.getStatusSale().getStatus() == null ? StatusSaleName.EXCHANGE_AUTHORIZED.getValue() == null : saleResponse.getStatusSale().getStatus().equals(StatusSaleName.EXCHANGE_AUTHORIZED.getValue())) ||
                (saleResponse.getStatusSale().getStatus() == null ? StatusSaleName.EXCHANGE_REQUESTED.getValue() == null : saleResponse.getStatusSale().getStatus().equals(StatusSaleName.EXCHANGE_REQUESTED.getValue()))
            ) continue;
            
            dtoList.add(saleResponse);
        }

        DataResponse data = new DataResponse();
        data.setEntities(dtoList);
        
        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
