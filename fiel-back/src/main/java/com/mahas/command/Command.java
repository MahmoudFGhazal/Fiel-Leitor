package com.mahas.command;

import com.mahas.domain.FacadeRequest;

public class Command {
    private ICommand command;

    public void setCommand(ICommand command){
        this.command = command;
    }
    
    public String execute(FacadeRequest request){
        return command.execute(request);
    }
}
