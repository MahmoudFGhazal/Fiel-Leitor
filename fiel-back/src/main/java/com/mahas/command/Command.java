package com.mahas.command;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;

public class Command {
    private ICommand command;

    public void setCommand(ICommand command){
        this.command = command;
    }
    
    public SQLRequest execute(FacadeRequest request){
        return command.execute(request);
    }
}
