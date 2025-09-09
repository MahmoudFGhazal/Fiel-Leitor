package com.mahas.dao.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class AddressDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public SQLResponse query(FacadeRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof Address)) {
            return null;
        }

        Address address = (Address) entity;

        StringBuilder jpql = new StringBuilder("SELECT a FROM Address a WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(a) FROM Address a WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (address.getId() != null) {
            whereClause.append(" AND a.id = :id");
            parameters.put("id", address.getId());
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        Integer page = request.getPage() != null ? request.getPage() : 1;
        Integer limit = request.getLimit();
        int offset = (limit != null) ? (page - 1) * limit : 0;

        try {
            Query query = entityManager.createQuery(jpql.toString(), Address.class);
            parameters.forEach(query::setParameter);

            if (limit != null) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            @SuppressWarnings("unchecked")
            List<Address> resultList = query.getResultList();

            Query countQuery = entityManager.createQuery(countJpql.toString());
            parameters.forEach(countQuery::setParameter);
            Number totalCount = (Number) countQuery.getSingleResult();
            int totalItems = totalCount.intValue();

            int totalPage = (limit != null) ? (int) Math.ceil((double) totalItems / limit) : 1;

            if (!resultList.isEmpty()) {
                if (limit != null && limit == 1) {
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
            e.printStackTrace();
            throw e;
        }

        return response;
    }

    public SQLResponse save(FacadeRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Address)){
            return null;
        }

        Address address = (Address) entity;

        try {
            entityManager.persist(address);

            entityManager.flush();

            response.setEntity(address);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

        return response;
    }

    public SQLResponse delete(FacadeRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof Address)) {
            return null;
        }

        Address address = (Address) entity;

        String jpql = "DELETE FROM Address a WHERE a.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", address.getId());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(address); 
            }else {
                response.setEntity(null);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

        return response;
    }

}
