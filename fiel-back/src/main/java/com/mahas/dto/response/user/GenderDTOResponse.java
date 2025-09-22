package com.mahas.dto.response.user;

import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenderDTOResponse implements DTOResponse {
    private Integer id;
    private String gender;
}
