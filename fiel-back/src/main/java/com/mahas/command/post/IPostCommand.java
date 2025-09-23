package com.mahas.command.post;

import com.mahas.domain.DataResponse;
import com.mahas.domain.SQLResponse;

public interface IPostCommand {
    DataResponse execute(SQLResponse sqlResponse);
}
