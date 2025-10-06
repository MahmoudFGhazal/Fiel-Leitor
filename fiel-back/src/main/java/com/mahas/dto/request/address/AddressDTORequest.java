package com.mahas.dto.request.address;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTORequest implements DTORequest {
    private Integer id;
    private Boolean principal;
    private Boolean active;
    private String nickname;
    private Integer number;
    private String complement;
    private String street;
    private String neighborhood;
    private String zip;
    private String city;
    private String state;
    private String country;
    private Integer user;
    private Integer streetType;
    private Integer residenceType;
}
