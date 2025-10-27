package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BookValidator;
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
public class VerifyDeleteBookCommand implements IPreCommand {
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

        communValidator.validateNotBlanck(bookRequest.getId(), "Id");

        // Verificar se o usuário existe
        if (!bookValidator.bookExists(bookRequest.getId())) {
            throw new ValidationException("Livro não encontrado");
        }

        Book book = new Book();

        book.setId(bookRequest.getId());
        book.setIsDelete(true);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(book);
        return sqlRequest;
    }
}
