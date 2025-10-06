package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.domain.product.Book;
import com.mahas.domain.product.Category;
import com.mahas.dto.request.product.BookDTORequest;

@Component
public class BookValidator {
    public Book toEntity(BookDTORequest dto) {
        if (dto == null) return null;

        Book book = new Book();
        book.setId(dto.getId() != null ? dto.getId().intValue() : null);
        book.setName(dto.getName());
        book.setActive(dto.getActive());
        book.setStock(dto.getStock());

        if (dto.getCategory() != null) {
            Category c = new Category();
            c.setId(dto.getCategory());
            book.setCategory(c);
        }

        return book;
    }
}
