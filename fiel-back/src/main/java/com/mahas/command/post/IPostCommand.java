package com.mahas.command.post;

import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;

public interface IPostCommand {
    FacadeResponse execute(SQLResponse sqlResponse);
}
