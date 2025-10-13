package com.mahas.dao.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.User;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Component
public class UserDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SQLResponse query(SQLRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof User)) {
            return null;
        }

        User user = (User) entity;
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(u) FROM User u WHERE 1=1");

        Map<String, Object> params = new HashMap<>();
        StringBuilder where = new StringBuilder();

        if (user.getId() != null) {
            where.append(" AND u.id = :id");
            params.put("id", user.getId());
        }
        if (user.getName() != null && !user.getName().isEmpty()) {
            where.append(" AND LOWER(u.name) LIKE LOWER(:name)");
            params.put("name", "%" + user.getName() + "%");
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            where.append(" AND LOWER(u.email) = LOWER(:email)");
            params.put("email", user.getEmail());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            where.append(" AND LOWER(u.password) = LOWER(:password)");
            params.put("password", user.getPassword());
        }
        if (user.getCpf() != null && !user.getCpf().isEmpty()) {
            where.append(" AND LOWER(u.cpf) = LOWER(:cpf)");
            params.put("cpf", user.getCpf());
        }
        if (user.getActive() != null) {
            where.append(" AND u.active = :active");
            params.put("active", user.getActive());
        }

        jpql.append(where); 
        countJpql.append(where);

        int page = request.getPage(); 
        int limit = request.getLimit();
        int offset = (limit > 0) ? (page - 1) * limit : 0;

        TypedQuery<User> query = entityManager.createQuery(jpql.toString(), User.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<User> resultList = query.getResultList();

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
        if(!(entity instanceof User)){
            return null;
        }

        User user = (User) entity;

        try {
            entityManager.persist(user);

            entityManager.flush();

            response.setEntity(user);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }

    @Override
    public SQLResponse delete(SQLRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if (!(entity instanceof User)) {
            return null;
        }

        User user = (User) entity;

        String jpql = "DELETE FROM User u WHERE u.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", user.getId());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(user); 
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
        if(!(entity instanceof User)){
            return null;
        }

        User user = (User) entity;

        try {
            User existingUser = entityManager.find(User.class, user.getId());
            if (existingUser == null) {
                response.setEntity(null);
                return response;
            }
            
            if (user.getName() != null) {
                existingUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getCpf() != null) {
                existingUser.setCpf(user.getCpf());
            }
            if (user.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(user.getPhoneNumber());
            }
            if (user.getGender() != null) {
                existingUser.setGender(user.getGender());
            }
            if (user.getBirthday() != null) {
                existingUser.setBirthday(user.getBirthday());
            }
            if (user.getActive() != null) {
                existingUser.setActive(user.getActive());
            }

            entityManager.flush();

            response.setEntity(existingUser);
        } catch (PersistenceException e) {
            throw e;
        }

        return response;
    }
}
