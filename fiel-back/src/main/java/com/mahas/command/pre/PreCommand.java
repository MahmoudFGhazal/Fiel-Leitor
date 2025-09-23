package com.mahas.command.pre;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;

public class PreCommand {
    private IPreCommand command;

    public void setCommand(IPreCommand command){
        this.command = command;
    }
    
    public SQLRequest execute(FacadeRequest request){
        return command.execute(request);
    }
}
