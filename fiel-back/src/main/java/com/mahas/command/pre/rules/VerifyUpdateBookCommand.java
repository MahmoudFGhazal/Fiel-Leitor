package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CategoryValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Book;
import com.mahas.domain.product.Category;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyUpdateBookCommand implements IPreCommand {
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
       
        communValidator.validateNotBlanck(bookRequest.getId(), "Id");

        Book book = new Book();

        if (bookRequest.getName() != null) {
            book.setName(bookRequest.getName());
        }

        if (bookRequest.getPrice() != null) {
            book.setPrice(bookRequest.getPrice());
        }

        if (bookRequest.getCategory() != null) {
            categoryValidator.categoryExists(bookRequest.getCategory());
            
            Category category = new Category();
            category.setId(bookRequest.getCategory());
            book.setCategory(category);
        }

        if (bookRequest.getStock() != null) {
            book.setStock(bookRequest.getStock());
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(book); 
        return sqlRequest;
    }
}
