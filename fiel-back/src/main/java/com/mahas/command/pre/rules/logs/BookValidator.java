package com.mahas.command.pre.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.base.product.BaseBookCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.product.Book;
import com.mahas.domain.product.Category;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

@Component
public class BookValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseBookCommand baseBookCommand;
   
    public Book toEntity(BookDTORequest dto) {
        if (dto == null) return null;

        Book book = new Book();
        book.setId(dto.getId() != null ? dto.getId().intValue() : null);
        book.setName(dto.getName());
        book.setPrice(dto.getPrice());
        book.setActive(dto.getActive());
        book.setStock(dto.getStock());

        if (dto.getCategory() != null) {
            Category c = new Category();
            c.setId(dto.getCategory());
            book.setCategory(c);
        }

        return book;
    }

    public boolean bookExists(Integer id) {
        BookDTORequest book = new BookDTORequest();
        book.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(book);
        request.setLimit(1);
        request.setPreCommand(baseBookCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

    public Integer checkBookStock(Integer id, Integer quantity) {
        BookDTORequest book = new BookDTORequest();
        book.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(book);
        request.setLimit(1);
        request.setPreCommand(baseBookCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        if(entity == null) {
            throw new ValidationException("Livro não encontrado");
        }

        BookDTOResponse bookResponse = (BookDTOResponse) entity;

        if(bookResponse.getStock() < quantity) {
            throw new ValidationException("Quantidade maior que o estoque");
        }

        return bookResponse.getStock();
    }
}
