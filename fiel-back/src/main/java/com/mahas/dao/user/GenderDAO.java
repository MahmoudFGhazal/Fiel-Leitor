package com.mahas.dao.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.Gender;
import com.mahas.domain.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class GenderDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public SQLResponse query(FacadeRequest request) {
       SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof User)) {
            return null;
        }

        Gender gender = (Gender) entity;
        StringBuilder sql = new StringBuilder("SELECT * FROM genders WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM genders WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (gender.getId() != null) {
            whereClause.append(" AND id = :id");
            parameters.put("id", gender.getId());
        }
        if (gender.getName() != null && !gender.getName().isBlank()) {
            whereClause.append(" AND LOWER(name) LIKE LOWER(:name)");
            parameters.put("name", "%" + gender.getName() + "%");
        }

        sql.append(whereClause);
        countSql.append(whereClause);

        Integer page = request.getPage() != null ? request.getPage() : 1;
        Integer limit = request.getLimit() != null ? request.getLimit() : 10;
        int offset = (page - 1) * limit;

        sql.append(" LIMIT :limit OFFSET :offset");

        Query nativeQuery = entityManager.createNativeQuery(sql.toString(), User.class);
        parameters.forEach(nativeQuery::setParameter);
        nativeQuery.setParameter("limit", limit);
        nativeQuery.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<User> resultList = nativeQuery.getResultList();

        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        parameters.forEach(countQuery::setParameter);
        Number totalCount = ((Number) countQuery.getSingleResult());

        int totalItems = totalCount.intValue();
        int pageCount = (int) Math.ceil((double) totalItems / limit);

        response.setEntities(new ArrayList<>(resultList));
        response.setEntity(resultList.isEmpty() ? null : resultList.get(0));
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage(totalItems);
        response.setPageCount(pageCount);

        return response;
    }
}
