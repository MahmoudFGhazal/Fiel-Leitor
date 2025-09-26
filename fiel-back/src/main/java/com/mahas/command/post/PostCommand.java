package com.mahas.command.post;

import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;

public class PostCommand {
    private IPostCommand command;

    public void setCommand(IPostCommand command){
        this.command = command;
    }
    
    public FacadeResponse execute(SQLResponse request){
        return command.execute(request);
    }
}
