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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class GenderDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public SQLResponse query(FacadeRequest request) {
        SQLResponse response = new SQLResponse();
        System.out.println("Oi");
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Gender)) {
            return null;
        }
        System.out.println("oi2");
        Gender gender = (Gender) entity;

        StringBuilder jpql = new StringBuilder("SELECT g FROM Gender g WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(g) FROM Gender g WHERE 1=1");
        System.out.println("oi3");
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();
        System.out.println("oi4");
        if (gender.getId() != null) {
            whereClause.append(" AND g.id = :id");
            parameters.put("id", gender.getId());
        }
        if (gender.getGender() != null && !gender.getGender().isBlank()) {
            whereClause.append(" AND LOWER(g.name) LIKE LOWER(:name)");
            parameters.put("name", "%" + gender.getGender() + "%");
        }
        System.out.println("oi5");
        jpql.append(whereClause);
        countJpql.append(whereClause);

        Integer page = request.getPage() != null ? request.getPage() : 1;
        Integer limit = request.getLimit() != null ? request.getLimit() : 10;
        int offset = (page - 1) * limit;

        try {
            Query query = entityManager.createQuery(jpql.toString(), Gender.class);
            parameters.forEach(query::setParameter);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            System.out.println("Oi6");
            @SuppressWarnings("unchecked")
            List<Gender> resultList = query.getResultList();

            Query countQuery = entityManager.createQuery(countJpql.toString());
            parameters.forEach(countQuery::setParameter);
            Number totalCount = (Number) countQuery.getSingleResult();
            System.out.println("oi7");
            int totalItems = totalCount.intValue();
            int totalPage = (int) Math.ceil((double) totalItems / limit);
            int pageCount = totalPage;
            System.out.println("oi8");
            if (resultList.size() == 1) {
                response.setEntity(resultList.get(0));
            } else if (!resultList.isEmpty()) {
                response.setEntities(new ArrayList<>(resultList));
            }
            response.setPage(page);
            response.setLimit(limit);
            response.setTotalItem(totalItems);
            response.setTotalPage(totalPage);
            response.setPageCount(pageCount);

        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

        return response;
    }
}
