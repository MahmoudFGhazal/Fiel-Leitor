package com.mahas.dto.request.user;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTORequest implements DTORequest {
    private Integer id;
    private Integer user;
    private Boolean principal;
    private String bin;
    private String last4;
    private String holder;
    private String expMonth;
    private String expYear;
    private String brand;
}
