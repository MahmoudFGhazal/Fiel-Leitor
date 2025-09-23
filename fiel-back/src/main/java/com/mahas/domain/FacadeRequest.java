package com.mahas.domain;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.command.pre.IPreCommand;
import com.mahas.dto.request.DTORequest;

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
    private DTORequest entity;
    
    private IPreCommand preCommand;
    private IPostCommand postCommand;

    private int page;
    private int limit;
}