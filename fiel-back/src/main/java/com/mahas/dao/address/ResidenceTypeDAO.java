package com.mahas.dao.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.ResidenceType;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class ResidenceTypeDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof ResidenceType)) {
            return null;
        }

        ResidenceType residenceType = (ResidenceType) entity;

        StringBuilder jpql = new StringBuilder("SELECT r FROM ResidenceType r WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(r) FROM ResidenceType r WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (residenceType.getId() != null) {
            whereClause.append(" AND r.id = :id");
            parameters.put("id", residenceType.getId());
        }
        if (residenceType.getResidenceType() != null && !residenceType.getResidenceType().isBlank()) {
            whereClause.append(" AND LOWER(r.typeResidence) LIKE LOWER(:typeResidence)");
            parameters.put("typeResidence", "%" + residenceType.getResidenceType() + "%");
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        int page = request.getPage() > 0 ? request.getPage() : 1;
        int limit = request.getLimit() > 0 ? request.getLimit() : 0;
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        try {
            Query query = entityManager.createQuery(jpql.toString(), ResidenceType.class);
            parameters.forEach(query::setParameter);

            if (limit > 0) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            @SuppressWarnings("unchecked")
            List<ResidenceType> resultList = query.getResultList();

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
