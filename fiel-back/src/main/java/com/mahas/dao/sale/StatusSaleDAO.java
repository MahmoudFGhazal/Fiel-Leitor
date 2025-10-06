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
import com.mahas.domain.sale.StatusSale;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class StatusSaleDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof StatusSale)) return null;

        StatusSale status = (StatusSale) entity;

        StringBuilder jpql = new StringBuilder("SELECT s FROM StatusSale s WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(s) FROM StatusSale s WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (status.getId() != null) { 
            where.append(" AND s.id = :id"); 
            params.put("id", status.getId()); 
        }
        if (status.getName() != null) { 
            where.append(" AND LOWER(s.name) LIKE LOWER(:name)"); 
            params.put("name", "%" + status.getName() + "%"); 
        }

        jpql.append(where); countJpql.append(where);

        int page = request.getPage(); int limit = request.getLimit(); int offset = (limit > 0) ? (page - 1) * limit : 0;

        Query query = entityManager.createQuery(jpql.toString(), StatusSale.class);
        params.forEach(query::setParameter);
        if (limit > 0) { query.setFirstResult(offset); query.setMaxResults(limit); }

        List<StatusSale> resultList = query.getResultList();
        Query countQuery = entityManager.createQuery(countJpql.toString());
        params.forEach(countQuery::setParameter);
        int totalItems = ((Number) countQuery.getSingleResult()).intValue();
        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) response.setEntity(resultList.get(0));
            else response.setEntities(new ArrayList<>(resultList));
        }

        response.setPage(page); response.setLimit(limit); response.setTotalItem(totalItems); response.setTotalPage(totalPage);
        return response;
    }
}
