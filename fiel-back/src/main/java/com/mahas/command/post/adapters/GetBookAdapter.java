package com.mahas.command.post.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Book;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.dto.response.product.CategoryDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class GetBookAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        List<DomainEntity> entities = sqlResponse.getEntities();
        if (entities != null && !entities.isEmpty()) {
            List<DTOResponse> dtoList = new ArrayList<>(entities.size());

            for (DomainEntity e : entities) {
                if (!(e instanceof Book)) {
                    throw new ValidationException("Tipo de entidade inválido, esperado Book");
                }

                BookDTOResponse bookResponse = new BookDTOResponse();
                bookResponse.mapFromEntity(e);
                
                dtoList.add(bookResponse);
            }

            DataResponse data = new DataResponse();
            data.setEntities(dtoList);
            
            FacadeResponse response = new FacadeResponse();
            response.setData(data);

            return response;
        }

        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Book)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Book");
        }

        Book book = (Book) entity;

        BookDTOResponse bookResponse = new BookDTOResponse();
        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setStock(book.getStock());

        CategoryDTOResponse categoryResponse = new CategoryDTOResponse();
        categoryResponse.setId(book.getCategory().getId());
        categoryResponse.setName(book.getCategory().getName());
        bookResponse.setCategory(categoryResponse);
        System.out.println("iu");
        DataResponse data = new DataResponse();
        data.setEntity(bookResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
