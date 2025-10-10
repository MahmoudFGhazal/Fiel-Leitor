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
import com.mahas.domain.user.Gender;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (gender.getId() != null) {
            where.append(" AND g.id = :id");
            params.put("id", gender.getId());
        }
        if (gender.getGender() != null && !gender.getGender().isEmpty()) {
            where.append(" AND LOWER(g.gender) LIKE LOWER(:gender)");
            params.put("gender", "%" + gender.getGender() + "%");
        }

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<Gender> query = entityManager.createQuery(jpql.toString(), Gender.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<Gender> resultList = query.getResultList();

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
}
