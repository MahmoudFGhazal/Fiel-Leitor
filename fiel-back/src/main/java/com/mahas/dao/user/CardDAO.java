package com.mahas.dao.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.Card;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Component
public class CardDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Card)) return null;

        Card card = (Card) entity;

        StringBuilder jpql = new StringBuilder("SELECT c FROM Card c WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(c) FROM Card c WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (card.getId() != null) { 
            where.append(" AND c.id = :id"); 
            params.put("id", card.getId()); 
        }
        if (card.getUser() != null && card.getUser().getId() != null) { 
            where.append(" AND c.user.id = :userId"); 
            params.put("userId", card.getUser().getId()); 
        }

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<Card> query = entityManager.createQuery(jpql.toString(), Card.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<Card> resultList = query.getResultList();

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
        if(!(entity instanceof Card)){
            return null;
        }

        Card card = (Card) entity;

        try {
            entityManager.persist(card);

            entityManager.flush();

            response.setEntity(card);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse delete(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Card)) {
            return null;
        }

        Card card = (Card) entity;

        String jpql = "DELETE FROM Card c WHERE c.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", card.getId());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(card); 
            }else {
                response.setEntity(null);
            }
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

}
