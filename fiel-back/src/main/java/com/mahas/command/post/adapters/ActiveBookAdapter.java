package com.mahas.command.post.adapters;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Book;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class ActiveBookAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Book)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Book");
        }

        Book book = (Book) entity;

        BookDTOResponse bookResponse = new BookDTOResponse();
        bookResponse.setId(book.getId());
        bookResponse.setActive(book.getActive());
        
        DataResponse data = new DataResponse();
        data.setEntity(bookResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
