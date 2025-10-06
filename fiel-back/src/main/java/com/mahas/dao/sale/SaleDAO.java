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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class SaleDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof Sale)) {
            return null;
        }

        Sale sale = (Sale) entity;

        StringBuilder jpql = new StringBuilder("SELECT s FROM Sale s WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(s) FROM Sale s WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (sale.getId() != null) {
            whereClause.append(" AND s.id = :id");
            parameters.put("id", sale.getId());
        }
        if (sale.getUser() != null && sale.getUser().getId() != null) {
            whereClause.append(" AND s.user.id = :userId");
            parameters.put("userId", sale.getUser().getId());
        }
        if (sale.getStatusSale() != null && sale.getStatusSale().getId() != null) {
            whereClause.append(" AND s.statusSale.id = :statusId");
            parameters.put("statusId", sale.getStatusSale().getId());
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        int page = request.getPage();
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        Query query = entityManager.createQuery(jpql.toString(), Sale.class);
        parameters.forEach(query::setParameter);

        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<Sale> resultList = query.getResultList();

        Query countQuery = entityManager.createQuery(countJpql.toString());
        parameters.forEach(countQuery::setParameter);
        Number totalCount = (Number) countQuery.getSingleResult();
        int totalItems = totalCount.intValue();

        int totalPage = (limit > 0) ? (int) Math.ceil((double) totalItems / limit) : 1;

        if (!resultList.isEmpty()) {
            if (limit == 1) {
                response.setEntity(resultList.get(0));
            } else {
                response.setEntities(new ArrayList<>(resultList));
            }
        }

        response.setPage(page);
        response.setLimit(limit);
        response.setTotalItem(totalItems);
        response.setTotalPage(totalPage);

        return response;
    }
}
