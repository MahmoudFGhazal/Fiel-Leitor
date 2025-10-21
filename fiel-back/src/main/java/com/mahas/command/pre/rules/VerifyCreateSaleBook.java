package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.base.product.BaseBookCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.SaleBookValidator;
import com.mahas.command.pre.rules.logs.SaleValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.SaleBook;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.dto.request.sale.SaleBookDTORequest;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

@Component
public class VerifyCreateSaleBook implements IPreCommand {
    @Autowired
    Facade facade;
   
    @Autowired
    private SaleBookValidator saleBookValidator;

    @Autowired
    private SaleValidator saleValidator;

    @Autowired
    private BaseBookCommand baseBookCommand;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof SaleBookDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado SaleBookDTORequest");
        }

        SaleBookDTORequest saleBookRequest = (SaleBookDTORequest) entity;

        communValidator.validateNotBlanck(saleBookRequest.getSale(), "Venda");
        communValidator.validateNotBlanck(saleBookRequest.getBook(), "Livro");
        communValidator.validateNotBlanck(saleBookRequest.getQuantity(), "Quantidade");
        
        //Validar Vendas
        saleValidator.saleExists(saleBookRequest.getSale());

        //Validar Percent
        if (saleBookRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantidade inválida");
        }

        SaleBook saleBook = saleBookValidator.toEntity(saleBookRequest);
        saleBook.setId(null);

        FacadeRequest req = new FacadeRequest();
        BookDTORequest bookReq = new BookDTORequest();

        bookReq.setId(saleBookRequest.getBook());
        req.setLimit(1);
        req.setPreCommand(baseBookCommand);
        req.setEntity(bookReq);

        FacadeResponse res = facade.query(req);

        if(res.getData().getEntity() == null) {
            throw new ValidationException("Livro não encontrado");
        }

        BookDTOResponse bookRes = (BookDTOResponse) res.getData().getEntity();

        if(saleBookRequest.getQuantity() > bookRes.getStock()) {
            throw new ValidationException("Quantidade maior que o estoque existente");
        }

        saleBook.setPrice(bookRes.getPrice());

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(saleBook);

        return sqlRequest;
    }
}
