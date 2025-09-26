package com.mahas.command.post.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.dto.response.address.ResidenceTypeDTOResponse;
import com.mahas.dto.response.address.StreetTypeDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class CreateAddressAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Address)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Address");
        }

        Address address = (Address) entity;

        AddressDTOResponse addressResponse = new AddressDTOResponse();
        addressResponse.setId(address.getId());
        addressResponse.setCity(address.getCity());
        addressResponse.setComplement(address.getComplement());
        addressResponse.setCountry(address.getCountry());
        addressResponse.setNeighborhood(address.getNeighborhood());
        addressResponse.setNickname(address.getNickname());
        addressResponse.setNumber(address.getNumber());

        ResidenceTypeDTOResponse residenceTypeResponse = new ResidenceTypeDTOResponse();
        residenceTypeResponse.setId(address.getResidenceType().getId());
        residenceTypeResponse.setResidenceType(address.getResidenceType().getResidenceType());

        StreetTypeDTOResponse streetTypeResponse = new StreetTypeDTOResponse();
        streetTypeResponse.setId(address.getStreetType().getId());
        streetTypeResponse.setStreetType(address.getStreetType().getStreetType());

        addressResponse.setResidenceType(residenceTypeResponse);
        addressResponse.setStreetType(streetTypeResponse);
        System.out.println(addressResponse.getId());
        
        DataResponse data = new DataResponse();
        data.setEntity(addressResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
