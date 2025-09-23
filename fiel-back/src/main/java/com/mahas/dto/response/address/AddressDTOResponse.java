package com.mahas.dto.response.address;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.address.Address;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTOResponse implements DTOResponse {
    private Integer id;
    private String nickname;
    private Integer number;
    private String complement;
    private String street;
    private String neighborhood;
    private String zip;
    private String city;
    private String state;
    private String country;
    private UserDTOResponse user;
    private StreetTypeDTOResponse streetType;
    private ResidenceTypeDTOResponse residenceType;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Address a) {
            this.id = a.getId();
            this.nickname = a.getNickname();
            this.number = a.getNumber();
            this.complement = a.getComplement();
            this.street = a.getStreet();
            this.neighborhood = a.getNeighborhood();
            this.zip = a.getZip();
            this.city = a.getCity();
            this.state = a.getState();
            this.country = a.getCountry();

            if (a.getUser() != null) {
                this.user = new UserDTOResponse();
                this.user.mapFromEntity(a.getUser());
            }

            if (a.getStreetType() != null) {
                this.streetType = new StreetTypeDTOResponse();
                this.streetType.mapFromEntity(a.getStreetType());
            }

            if (a.getResidenceType() != null) {
                this.residenceType = new ResidenceTypeDTOResponse();
                this.residenceType.mapFromEntity(a.getResidenceType());
            }
        }
    }
}
