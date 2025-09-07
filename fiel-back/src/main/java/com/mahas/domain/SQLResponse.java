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
    DomainEntity entity;
    List<DomainEntity> entities;
    Integer page;
    Integer limit;
    Integer pageCount;
    Integer totalPage;

}
