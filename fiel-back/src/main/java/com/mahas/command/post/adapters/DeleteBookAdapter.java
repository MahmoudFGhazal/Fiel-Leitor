package com.mahas.command.post.adapters;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Book;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.exception.ValidationException;

import org.springframework.stereotype.Component;

@Component
public class DeleteBookAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Book)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Book");
        }

        Book book = (Book) entity;

        AddressDTOResponse addressResponse = new AddressDTOResponse();
        addressResponse.setId(book.getId());

        DataResponse data = new DataResponse();
        data.setEntity(addressResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
