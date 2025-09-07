package com.mahas.command;

import com.mahas.domain.FacadeRequest;

public interface ICommand {
    String execute(FacadeRequest request);
}
