package com.mahas.command.pre.rules.logs;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.base.user.BaseCardCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Card;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.user.CardDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

@Component
public class CardValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseCardCommand baseCardCommand;
   
    public Card toEntity(CardDTORequest dto) {
        if (dto == null) return null;

        Card card = new Card();
        card.setId(dto.getId() != null ? dto.getId().intValue() : null);
        card.setPrincipal(dto.getPrincipal());
        card.setBin(dto.getBin());
        card.setLast4(dto.getLast4());
        card.setHolder(dto.getHolder());
        card.setExpMonth(dto.getExpMonth());
        card.setExpYear(dto.getExpYear());
        card.setBrand(dto.getBrand());

        if (dto.getUser() != null) {
            User u = new User();
            u.setId(dto.getUser());
            card.setUser(u);
        }

        return card;
    }

    public boolean cardExists(Integer id) {
        CardDTORequest card = new CardDTORequest();
        card.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(card);
        request.setLimit(1);
        request.setPreCommand(baseCardCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

    public boolean userHasCard(Integer id) {
        CardDTORequest card = new CardDTORequest();
        card.setUser(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(card);
        request.setLimit(1);
        request.setPreCommand(baseCardCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

    public void isValidNumberFormat(String bin, String last4) {
        if (bin.length() != 6) {
            throw new ValidationException("Bin inválido");
        }

        if (last4.length() != 6) {
            throw new ValidationException("Last4 inválido");
        }
    }

    public boolean cardNumberExists(String bin, String last4) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseCardCommand);
        CardDTORequest card = new CardDTORequest();
        card.setBin(bin);
        card.setLast4(last4);
        request.setEntity(card); 

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }

    public void isValidBin(String bin) {
        if (!bin.matches("\\d{6}")) {
            throw new ValidationException("BIN deve conter exatamente 6 dígitos numéricos");
        }
    }

    public void isValidLast4(String last4) {
        if (!last4.matches("\\d{4}")) {
            throw new ValidationException("Últimos 4 dígitos devem conter exatamente 4 números");
        }
    }

    public void isValidExp(String expMonth, String expYear) {
        if (!expMonth.matches("\\d{2}")) {
            throw new ValidationException("Mês de expiração deve conter 2 dígitos");
        }

        int month = Integer.parseInt(expMonth);
        if (month < 1 || month > 12) {
            throw new ValidationException("Mês de expiração inválido (deve estar entre 01 e 12)");
        }

        if (!expYear.matches("\\d{4}")) {
            throw new ValidationException("Ano de expiração deve conter 4 dígitos");
        }
        int year = Integer.parseInt(expYear);

        int currentYear = java.time.Year.now().getValue();
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            throw new ValidationException("Cartão expirado");
        }

        if (year > currentYear + 15) {
            throw new ValidationException("Ano de expiração muito distante no futuro");
        }
    }

    public void isUser(Integer user, Integer card) {
        isUser(user, new Integer[]{ card });
    }

    public void isUser(Integer user, Integer... cards) {
        CardDTORequest cardReq = new CardDTORequest();
        cardReq.setUser(user);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(cardReq);
        request.setPreCommand(baseCardCommand);

        FacadeResponse response = facade.query(request);

        if(response.getData().getEntities() == null) {
            throw new ValidationException("Cartão não existe");
        }

        List<DTOResponse> dtoList = response.getData().getEntities();
        if (dtoList == null) dtoList = Collections.emptyList();

        if (dtoList.isEmpty()) {
            throw new ValidationException("Usuário não possui cartões cadastrados");
        }

        List<CardDTOResponse> cardsRes = dtoList.stream()
            .map(dto -> (CardDTOResponse) dto)
            .toList();

        Set<Integer> userCardIds = cardsRes.stream()
            .map(CardDTOResponse::getId)
            .collect(Collectors.toSet());

        for (Integer cardId : cards) {
            if (!userCardIds.contains(cardId)) {
                throw new ValidationException("O cartão de ID " + cardId + " não pertence ao usuário");
            }
        }
    }
}
