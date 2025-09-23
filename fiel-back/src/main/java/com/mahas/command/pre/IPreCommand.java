package com.mahas.command.pre;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;

public interface IPreCommand {
    SQLRequest execute(FacadeRequest request);
}
