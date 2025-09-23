package com.mahas.command.post;

import com.mahas.domain.DataResponse;
import com.mahas.domain.SQLResponse;

public class PostCommand {
    private IPostCommand command;

    public void setCommand(IPostCommand command){
        this.command = command;
    }
    
    public DataResponse execute(SQLResponse request){
        return command.execute(request);
    }
}
