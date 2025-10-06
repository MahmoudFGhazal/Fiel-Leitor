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
import com.mahas.domain.product.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Component
public class CategoryDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Category)) return null;

        Category category = (Category) entity;

        StringBuilder jpql = new StringBuilder("SELECT c FROM Category c WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(c) FROM Category c WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (category.getId() != null) { where.append(" AND c.id = :id"); params.put("id", category.getId()); }
        if (category.getName() != null) { where.append(" AND LOWER(c.name) LIKE LOWER(:name)"); params.put("name", "%" + category.getName() + "%"); }

        jpql.append(where); countJpql.append(where);

        int page = request.getPage(); int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        Query query = entityManager.createQuery(jpql.toString(), Category.class);
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
