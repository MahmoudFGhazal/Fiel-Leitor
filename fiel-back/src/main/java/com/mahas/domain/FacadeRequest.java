package com.mahas.domain;

import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacadeRequest {
    private DomainEntity entity;
    private ICommand command;

    private Integer page;
    private Integer limit;
}