package com.mahas.domain;

import java.util.List;

import com.mahas.dto.response.DTOResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
    DTOResponse entity;
    List<DTOResponse> entities;
    
    Integer page;
    Integer limit;
    Integer pageCount;
    Integer totalItem;
    Integer totalPage;
}
