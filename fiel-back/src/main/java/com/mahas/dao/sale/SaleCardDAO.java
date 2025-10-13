package com.mahas.dao.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.SaleCard;
import com.mahas.domain.sale.SaleCardId;
import com.mahas.domain.user.Card;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

@Component
public class SaleCardDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof SaleCard)) return null;

        SaleCard sc = (SaleCard) entity;

        StringBuilder jpql = new StringBuilder("SELECT sc FROM SalesCard sc WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(sc) FROM SalesCard sc WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (sc.getSale() != null && sc.getSale().getId() != null) { 
            where.append(" AND sc.sale.id = :saleId"); 
            params.put("saleId", sc.getSale().getId()); 
        }
        if (sc.getCard() != null && sc.getCard().getId() != null) { 
            where.append(" AND sc.card.id = :cardId"); 
            params.put("cardId", sc.getCard().getId()); 
        }

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<SaleCard> query = entityManager.createQuery(jpql.toString(), SaleCard.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<SaleCard> resultList = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            countQuery.setParameter(e.getKey(), e.getValue());
        }

        long totalItems = countQuery.getSingleResult();
        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) response.setEntity(resultList.get(0));
            else response.setEntities(new ArrayList<>(resultList));
        }

        response.setPage(page); 
        response.setLimit(limit);
        response.setTotalItem((int) totalItems); 
        response.setTotalPage(totalPage);
        
        return response;
    }

    @Override
    public SQLResponse save(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof SaleCard)) {
            return null;
        }

        SaleCard saleCard = (SaleCard) entity;

        try {
            System.out.println(saleCard.getCard());
            System.out.println(saleCard.getSale());
            ensureEmbeddedId(saleCard);

            if (saleCard.getSale() != null && saleCard.getSale().getId() != null) {
                saleCard.setSale(entityManager.getReference(Sale.class, saleCard.getSale().getId()));
            }
            if (saleCard.getCard() != null && saleCard.getCard().getId() != null) {
                saleCard.setCard(entityManager.getReference(Card.class, saleCard.getCard().getId()));
            }

            entityManager.persist(saleCard);
            entityManager.flush();

            response.setEntity(saleCard);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    private void ensureEmbeddedId(SaleCard saleCard) {
        if (saleCard.getId() == null) {
            saleCard.setId(new SaleCardId());
        }

        if (saleCard.getSale() != null && saleCard.getSale().getId() != null) {
            saleCard.getId().setSaleId(saleCard.getSale().getId());
        }
        
        if (saleCard.getCard() != null && saleCard.getCard().getId() != null) {
            saleCard.getId().setCardId(saleCard.getCard().getId());
        }
    }
}
