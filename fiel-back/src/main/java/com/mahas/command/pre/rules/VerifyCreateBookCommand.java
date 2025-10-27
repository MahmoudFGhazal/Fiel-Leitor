package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BookValidator;
import com.mahas.command.pre.rules.logs.CategoryValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Book;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyCreateBookCommand implements IPreCommand {
    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof BookDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado BookDTORequest");
        }

        BookDTORequest bookRequest = (BookDTORequest) entity;
       
        communValidator.validateNotBlanck(bookRequest.getName(), "Nome");
        communValidator.validateNotBlanck(bookRequest.getCategory(), "Categoria");
        communValidator.validateNotBlanck(bookRequest.getPrice(), "Preço");

        categoryValidator.categoryExists(bookRequest.getCategory());

        if(bookRequest.getStock() == null) {
            bookRequest.setStock(0);
        }

        bookRequest.setActive(true);

        Book book = bookValidator.toEntity(bookRequest);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(book); 
        return sqlRequest;
    }
}
