package com.mahas.command.pre.base.product;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BookValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Book;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseBookCommand implements IPreCommand {
    @Autowired
    @Lazy
    BookValidator bookValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof BookDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado BookDTORequest");
        }

        BookDTORequest bookRequest = (BookDTORequest) entity;
        Book book = bookValidator.toEntity(bookRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(book);

        if(request.getLimit() != null && request.getLimit() > 0) {
            sqlRequest.setLimit(request.getLimit());
            if(request.getPage() != null && request.getPage() > 0) {
                sqlRequest.setPage(request.getPage());
            }else {
                sqlRequest.setPage(1);
            }
        }else {
            sqlRequest.setLimit(0);
            sqlRequest.setPage(1);
        }
        
        return sqlRequest;
    }
}
