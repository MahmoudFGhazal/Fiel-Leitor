package com.mahas.command.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.domain.FacadeRequest;

@Component
public class VerifyPagination implements ICommand {

    @Override
    public String execute(FacadeRequest request) {
        Integer limit = request.getLimit();
        Integer page = request.getPage();

        if(limit <= 0) {
            return "Quantidade de Limite Invalida";
        }

        if(page <= 0) {
            return "Quantidade de Pagina Invalida";
        }

        return null;
    }
}
