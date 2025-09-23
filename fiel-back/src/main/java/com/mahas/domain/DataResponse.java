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
    private DTOResponse entity;
    private List<DTOResponse> entities;
    
    private int page;
    private int limit;
    private int pageCount;
    private int totalItem;
    private int totalPage;
}
