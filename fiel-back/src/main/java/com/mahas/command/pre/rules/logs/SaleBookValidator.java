package com.mahas.command.pre.rules.logs;

import com.mahas.domain.product.Book;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.SaleBook;
import com.mahas.dto.request.sale.SaleBookDTORequest;

import org.springframework.stereotype.Component;

@Component
public class SaleBookValidator {
    public SaleBook toEntity(SaleBookDTORequest dto) {
        if (dto == null) return null;

        SaleBook sb = new SaleBook();
        sb.setQuantity(dto.getQuantity());
        sb.setPrice(dto.getPrice());

        if (dto.getSale() != null) {
            Sale s = new Sale();
            s.setId(dto.getSale());
            sb.setSale(s);
        }

        if (dto.getBook() != null) {
            Book b = new Book();
            b.setId(dto.getBook());
            sb.setBook(b);
        }

        return sb;
    }
}
