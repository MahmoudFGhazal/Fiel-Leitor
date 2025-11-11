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
import com.mahas.domain.address.Address;
import com.mahas.domain.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

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

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (address.getId() != null) {
            where.append(" AND a.id = :id");
            params.put("id", address.getId());
        }

        if (address.getUser() != null && address.getUser().getId() != null) {
            where.append(" AND a.user.id = :userId");
            params.put("userId", address.getUser().getId());
        }

        if (address.getPrincipal() != null) {
            where.append(" AND a.principal = :principal");
            params.put("principal", address.getPrincipal());
        }
        where.append(" AND a.isDelete = :isDelete");
        params.put("isDelete", false);

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<Address> query = entityManager.createQuery(jpql.toString(), Address.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<Address> resultList = query.getResultList();

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
            if (address.getIsDelete() != null) {
                existingAddress.setIsDelete(address.getIsDelete());
            }

            entityManager.flush();

            response.setEntity(existingAddress);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}
