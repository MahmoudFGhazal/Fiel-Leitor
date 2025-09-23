package com.mahas.domain;

import com.mahas.domain.sort.ISort;

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
public class SQLRequest {
    private DomainEntity entity;

    private int limit;
    private int page;
    private ISort sort;
}
