package com.mahas.dao.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Cart;
import com.mahas.domain.product.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class CartDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Cart)) return null;

        Cart cart = (Cart) entity;

        StringBuilder jpql = new StringBuilder("SELECT c FROM Cart c WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(c) FROM Cart c WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (cart.getUser() != null) { 
            where.append(" AND c.user = :user"); 
            params.put("user", cart.getUser()); 
        }

        jpql.append(where); countJpql.append(where);

        int page = request.getPage(); int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        Query query = entityManager.createQuery(jpql.toString(), Cart.class);
        params.forEach(query::setParameter);
        if (limit > 0) { query.setFirstResult(offset); query.setMaxResults(limit); }

        List<Category> resultList = query.getResultList();
        Query countQuery = entityManager.createQuery(countJpql.toString());
        params.forEach(countQuery::setParameter);
        int totalItems = ((Number) countQuery.getSingleResult()).intValue();
        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) response.setEntity(resultList.get(0));
            else response.setEntities(new ArrayList<>(resultList));
        }

        response.setPage(page); response.setLimit(limit);
        response.setTotalItem(totalItems); response.setTotalPage(totalPage);
        return response;
    }
}
