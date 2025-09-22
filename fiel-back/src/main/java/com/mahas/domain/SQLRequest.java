package com.mahas.domain;

import org.springframework.stereotype.Component;

import com.mahas.domain.sort.ISort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SQLRequest {
    private DomainEntity entity;

    private Integer limit;
    private Integer page;
    private ISort sort;
}
