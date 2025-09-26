package com.mahas.command.post.rules;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.exception.ValidationException;

public class DeleteAddressAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Address)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Address");
        }

        Address address = (Address) entity;

        AddressDTOResponse addressResponse = new AddressDTOResponse();
        addressResponse.setId(address.getId());

        DataResponse data = new DataResponse();
        data.setEntity(addressResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
