package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BookValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.StatusSaleValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleBookDTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyNewSale implements IPreCommand {
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private StatusSaleValidator statusSaleValidator;

    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof SaleDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado SaleDTORequest");
        }

        SaleDTORequest saleRequest = (SaleDTORequest) entity;

        communValidator.validateNotBlanck(saleRequest.getUser(), "Usuario");

        for(SaleBookDTORequest saleBook : saleRequest.getBooks()) {
            communValidator.validateNotBlanck(saleBook.getBook(), "Livro");
            communValidator.validateNotBlanck(saleBook.getQuantity(), "Quantidade de livros");
        }

        SQLRequest sqlRequest = new SQLRequest();

        //Validar Usuario
        userValidator.userExists(saleRequest.getUser());

        //Validar Livros
        for(SaleBookDTORequest saleBook : saleRequest.getBooks()) {
            if(!bookValidator.bookExists(saleBook.getBook())) {
                throw new ValidationException(saleBook.getBook().toString() + ": Livro não encontrado");
            }

            bookValidator.checkBookStock(saleBook.getBook(), saleBook.getQuantity());
        }
    
        Sale sale = new Sale();

        User user = new User();
        user.setId(saleRequest.getUser());
        sale.setUser(user);

        Integer statusSaleId = statusSaleValidator.getStatusSaleDefault();
        StatusSale statusSale = new StatusSale();
        statusSale.setId(statusSaleId);
        sale.setStatusSale(statusSale);

        sqlRequest.setEntity(sale);

        return sqlRequest;
    }
}
