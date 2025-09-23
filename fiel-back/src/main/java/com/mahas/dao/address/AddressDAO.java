package com.mahas.dao.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.user.User;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class AddressDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
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

        if (address.getUser() != null && address.getUser().getId() != null) {
            whereClause.append(" AND a.user.id = :userId");
            parameters.put("userId", address.getUser().getId());
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        int page = request.getPage() > 0 ? request.getPage() : 1;
        int limit = request.getLimit() > 0 ? request.getLimit() : 0;
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        try {
            Query query = entityManager.createQuery(jpql.toString(), Address.class);
            parameters.forEach(query::setParameter);

            if (limit > 0) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            @SuppressWarnings("unchecked")
            List<Address> resultList = query.getResultList();

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

        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse save(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Address)){
            return null;
        }

        Address address = (Address) entity;

        try {
            if (address.getUser() != null && address.getUser().getId() != null) {
                User managedUser = entityManager.find(User.class, address.getUser().getId());
                address.setUser(managedUser);
            }

            entityManager.persist(address);
            entityManager.flush();
            response.setEntity(address);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse delete(SQLRequest request) {
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
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse update(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof Address)){
            return null;
        }

        Address address = (Address) entity;

        try {
            Address existingAddress = entityManager.find(Address.class, address.getId());
            if (existingAddress == null) {
                response.setEntity(null);
                return response;
            }

            if (address.getNickname() != null) {
                existingAddress.setNickname(address.getNickname());
            }
            if (address.getNumber() != null) {
                existingAddress.setNumber(address.getNumber());
            }
            if (address.getComplement() != null) {
                existingAddress.setComplement(address.getComplement());
            }
            if (address.getStreet() != null) {
                existingAddress.setStreet(address.getStreet());
            }
            if (address.getNeighborhood() != null) {
                existingAddress.setNeighborhood(address.getNeighborhood());
            }
            if (address.getZip() != null) {
                existingAddress.setZip(address.getZip());
            }
            if (address.getCity() != null) {
                existingAddress.setCity(address.getCity());
            }
            if (address.getState() != null) {
                existingAddress.setState(address.getState());
            }
            if (address.getCountry() != null) {
                existingAddress.setCountry(address.getCountry());
            }
            if (address.getStreetType() != null) {
                existingAddress.setStreetType(address.getStreetType());
            }
            if (address.getResidenceType() != null) {
                existingAddress.setResidenceType(address.getResidenceType());
            }

            entityManager.flush();

            response.setEntity(existingAddress);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}
