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
public class ResidenceTypeDTORequest implements DTORequest {
    private Integer id;
    private String residenceType;
    private Boolean active;
}
