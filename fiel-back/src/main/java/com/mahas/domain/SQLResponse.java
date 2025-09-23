package com.mahas.domain;

import java.util.List;

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
public class SQLResponse {
    private DomainEntity entity;
    private List<DomainEntity> entities;
    
    private int page;
    private int limit;
    private int pageCount;
    private int totalItem;
    private int totalPage;

}
