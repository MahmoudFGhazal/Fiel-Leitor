package com.mahas.dto.response.address;

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
}
