package com.mahas.command.pre.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyPagination implements IPreCommand {

    @Override
    public SQLRequest execute(FacadeRequest request) {
        Integer limit = request.getLimit();
        Integer page = request.getPage();

        // Validar limite
        if (limit != null && limit <= 0) {
            throw new ValidationException("Quantidade de limite inválida");
        }

        // Validar página
        if (page != null && page <= 0) {
            throw new ValidationException("Quantidade de página inválida");
        }

        SQLRequest sqlRequest = new SQLRequest();
        return sqlRequest;
    }
}
