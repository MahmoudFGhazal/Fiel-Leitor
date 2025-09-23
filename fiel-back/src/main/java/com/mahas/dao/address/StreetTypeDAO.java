package com.mahas.dao.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.StreetType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class StreetTypeDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof StreetType)) {
            return null;
        }

        StreetType streetType = (StreetType) entity;

        StringBuilder jpql = new StringBuilder("SELECT s FROM StreetType s WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(s) FROM StreetType s WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (streetType.getId() != null) {
            whereClause.append(" AND s.id = :id");
            parameters.put("id", streetType.getId());
        }
        if (streetType.getStreetType() != null && !streetType.getStreetType().isBlank()) {
            whereClause.append(" AND LOWER(s.streetType) LIKE LOWER(:streetType)");
            parameters.put("streetType", "%" + streetType.getStreetType() + "%");
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        int page = request.getPage() > 0 ? request.getPage() : 1;
        int limit = request.getLimit() > 0 ? request.getLimit() : 0;
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        try {
            Query query = entityManager.createQuery(jpql.toString(), StreetType.class);
            parameters.forEach(query::setParameter);

            if (limit > 0) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            @SuppressWarnings("unchecked")
            List<StreetType> resultList = query.getResultList();

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
            response.setPageCount(totalPage);

        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}