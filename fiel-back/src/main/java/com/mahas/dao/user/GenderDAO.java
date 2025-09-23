package com.mahas.dao.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.Gender;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class GenderDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof Gender)) {
            return null;
        }

        Gender gender = (Gender) entity;

        StringBuilder jpql = new StringBuilder("SELECT g FROM Gender g WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(g) FROM Gender g WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (gender.getId() != null) {
            whereClause.append(" AND g.id = :id");
            parameters.put("id", gender.getId());
        }
        if (gender.getGender() != null && !gender.getGender().isEmpty()) {
            whereClause.append(" AND LOWER(g.gender) LIKE LOWER(:gender)");
            parameters.put("gender", "%" + gender.getGender() + "%");
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        int page = request.getPage();
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        try {
            Query query = entityManager.createQuery(jpql.toString(), Gender.class);
            parameters.forEach(query::setParameter);

            if (limit > 0) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            List<Gender> resultList = query.getResultList();

            Query countQuery = entityManager.createQuery(countJpql.toString());
            parameters.forEach(countQuery::setParameter);
            Number totalCount = (Number) countQuery.getSingleResult();
            int totalItems = totalCount.intValue();

            int totalPage = (int) Math.ceil((double) totalItems / limit);

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

        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}
