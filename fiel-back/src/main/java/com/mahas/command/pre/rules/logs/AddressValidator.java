package com.mahas.command.pre.rules.logs;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.base.address.BaseAddressCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.address.ResidenceType;
import com.mahas.domain.address.StreetType;
import com.mahas.domain.user.User;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

@Component
public class AddressValidator {
    @Autowired
    Facade facade;
    
    @Autowired
    BaseAddressCommand baseAddressCommand;
    
    public Address toEntity(AddressDTORequest dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setId(dto.getId());
        address.setPrincipal(dto.getPrincipal());
        address.setActive(dto.getActive());
        address.setNickname(dto.getNickname());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setStreet(dto.getStreet());
        address.setNeighborhood(dto.getNeighborhood());
        address.setZip(dto.getZip());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());

        // relacionamentos (carregados só pelo id)
        if (dto.getUser() != null) {
            User user = new User();
            user.setId(dto.getUser());
            address.setUser(user);
        }

        if (dto.getStreetType() != null) {
            StreetType streetType = new StreetType();
            streetType.setId(dto.getStreetType());
            address.setStreetType(streetType);
        }

        if (dto.getResidenceType() != null) {
            ResidenceType residenceType = new ResidenceType();
            residenceType.setId(dto.getResidenceType());
            address.setResidenceType(residenceType);
        }

        return address;
    }

    public boolean addressExists(Integer id) {
        AddressDTORequest address = new AddressDTORequest();
        address.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(address);
        request.setLimit(1);
        request.setPreCommand(baseAddressCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

    public boolean userHasAddress(Integer id) {
        AddressDTORequest address = new AddressDTORequest();
        address.setUser(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(address);
        request.setLimit(1);
        request.setPreCommand(baseAddressCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

    public void isValidZIPFormat(String zip) {
        if (zip.length() != 8) {
            throw new ValidationException("CEP inválido, deve conter 8 dígitos");
        }
    }

    public void isUser(Integer user, Integer address) {
        isUser(user, new Integer[]{ address });
    }

    public void isUser(Integer user, Integer... addresses) {
        AddressDTORequest addressReq = new AddressDTORequest();
        addressReq.setUser(user);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(addressReq);
        request.setPreCommand(baseAddressCommand);

        FacadeResponse response = facade.query(request);

        if(response.getData().getEntities() == null) {
            throw new ValidationException("Endereço não existe");
        }

        List<DTOResponse> dtoList = response.getData().getEntities();
        if (dtoList == null) dtoList = Collections.emptyList();

        if (dtoList.isEmpty()) {
            throw new ValidationException("Usuário não possui endereços cadastrados");
        }

        List<AddressDTOResponse> addressesRes = dtoList.stream()
            .map(dto -> (AddressDTOResponse) dto)
            .toList();

        Set<Integer> userAddressIds = addressesRes.stream()
            .map(AddressDTOResponse::getId)
            .collect(Collectors.toSet());

        for (Integer addressId : addresses) {
            if (!userAddressIds.contains(addressId)) {
                throw new ValidationException("O endereço de ID " + addressId + " não pertence ao usuário");
            }
        }
    }
}
