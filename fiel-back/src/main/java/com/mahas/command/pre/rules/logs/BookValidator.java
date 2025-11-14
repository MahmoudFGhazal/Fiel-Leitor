package com.mahas.command.pre.rules.logs;

import java.util.List;

import com.mahas.command.pre.base.product.BaseBookCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.product.Book;
import com.mahas.domain.product.Category;
import com.mahas.domain.product.PriceGroup;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseBookCommand baseBookCommand;
   
    public Book toEntity(BookDTORequest req) {

        Book b = new Book();

        b.setId(req.getId());
        b.setName(req.getName());
        b.setAuthor(req.getAuthor());
        b.setPublisher(req.getPublisher());
        b.setEdition(req.getEdition());
        b.setYear(req.getYear());

        b.setIsbn(req.getIsbn());
        b.setBarcode(req.getBarcode());
        b.setSynopsis(req.getSynopsis());
        b.setPages(req.getPages());

        b.setHeight(req.getHeight());
        b.setWidth(req.getWidth());
        b.setDepth(req.getDepth());
        b.setWeight(req.getWeight());

        b.setPrice(req.getPrice());
        b.setStock(req.getStock());
        b.setActive(req.getActive());

        // Grupo de preço
        if (req.getPriceGroupId() != null) {
            PriceGroup pg = new PriceGroup();
            pg.setId(req.getPriceGroupId());
            b.setPriceGroup(pg);
        }

        // Categorias N:N
        if (req.getCategories() != null) {
            List<Category> cats = req.getCategories()
                .stream()
                .map(id -> {
                    Category c = new Category();
                    c.setId(id);
                    return c;
                })
                .toList();

            b.setCategories(cats);
        }

        return b;
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
