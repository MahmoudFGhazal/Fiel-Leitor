package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        BookDTORequest dto = (BookDTORequest) entity;

        // --------------------------
        // VALIDAÇÕES DE CAMPOS
        // --------------------------
        communValidator.validateNotBlanck(dto.getName(), "Nome");
        communValidator.validateNotBlanck(dto.getAuthor(), "Autor");
        communValidator.validateNotBlanck(dto.getPublisher(), "Editora");
        communValidator.validateNotBlanck(dto.getEdition(), "Edição");
        communValidator.validateNotBlanck(dto.getYear(), "Ano");
        communValidator.validateNotBlanck(dto.getIsbn(), "ISBN");
        communValidator.validateNotBlanck(dto.getBarcode(), "Código de Barras");
        communValidator.validateNotBlanck(dto.getPages(), "Número de Páginas");
        communValidator.validateNotBlanck(dto.getPrice(), "Preço");
        communValidator.validateNotBlanck(dto.getPriceGroupId(), "Grupo de Precificação");

        if (dto.getCategories() == null || dto.getCategories().isEmpty())
            throw new ValidationException("Informe pelo menos uma categoria.");

        // validar cada categoria
        for (Integer catId : dto.getCategories()) {
            categoryValidator.categoryExists(catId);
        }

        // estoque default
        if (dto.getStock() == null) {
            dto.setStock(0);
        }

        dto.setActive(true);

        // construir entidade
        Book book = bookValidator.toEntity(dto);

        SQLRequest sql = new SQLRequest();
        sql.setEntity(book);

        return sql;
    }
}
