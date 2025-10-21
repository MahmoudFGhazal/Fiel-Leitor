package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BookValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Book;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class verifyReleaseReservedStock implements IPreCommand {
    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof BookDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado BookDTORequest");
        }

        BookDTORequest bookRequest = (BookDTORequest) entity;

        communValidator.validateNotBlanck(bookRequest.getId(), "Id não especificado");
        communValidator.validateNotBlanck(bookRequest.getStock(), "Id não especificado");
 
        SQLRequest sqlRequest = new SQLRequest();

         //Validar Livros
        if(!bookValidator.bookExists(bookRequest.getId())) {
            throw new ValidationException(bookRequest.getId().toString() + ": Livro não encontrado");
        }

        Integer actualStock = bookValidator.checkBookStock(bookRequest.getId(), bookRequest.getStock());
        Integer newStock = actualStock + bookRequest.getStock();

        Book book = new Book();
        book.setId(bookRequest.getId());
        book.setStock(newStock);

        sqlRequest.setEntity(book);

        return sqlRequest;
    }
}
