package com.mahas.command;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;

public interface ICommand {
    SQLRequest execute(FacadeRequest request);
}
