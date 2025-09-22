package com.mahas.dto.response.address;

import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeDTOResponse implements DTOResponse {
    private Integer id;
    private String streetType;
}
