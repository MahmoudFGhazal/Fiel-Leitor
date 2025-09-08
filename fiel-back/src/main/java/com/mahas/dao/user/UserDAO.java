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
import com.mahas.domain.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Component
public class UserDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public SQLResponse query(FacadeRequest request) {
        SQLResponse response = new SQLResponse();
        DomainEntity entity = request.getEntity();

        if (!(entity instanceof User)) {
            return null;
        }

        User user = (User) entity;

        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(u) FROM User u WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (user.getId() != null) {
            whereClause.append(" AND u.id = :id");
            parameters.put("id", user.getId());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            whereClause.append(" AND LOWER(u.name) LIKE LOWER(:name)");
            parameters.put("name", "%" + user.getName() + "%");
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            whereClause.append(" AND LOWER(u.email) = LOWER(:email)");
            parameters.put("email", user.getEmail());
        }
        if (user.getCpf() != null && !user.getCpf().isBlank()) {
            whereClause.append(" AND LOWER(u.cpf) = LOWER(:cpf)");
            parameters.put("cpf", user.getCpf());
        }
        if (user.getActive() != null) {
            whereClause.append(" AND u.active = :active");
            parameters.put("active", user.getActive());
        }

        jpql.append(whereClause);
        countJpql.append(whereClause);

        Integer page = request.getPage() != null ? request.getPage() : 1;
        Integer limit = request.getLimit();
        int offset = (limit != null) ? (page - 1) * limit : 0;

        try {
            Query query = entityManager.createQuery(jpql.toString(), User.class);
            parameters.forEach(query::setParameter);

            if (limit != null) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            @SuppressWarnings("unchecked")
            List<User> resultList = query.getResultList();

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
        if(!(entity instanceof User)){
            return null;
        }

        User user = (User) entity;

        try {
            entityManager.persist(user);

            entityManager.flush();

            response.setEntity(user);
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

        return response;
    }

    public SQLResponse delete(FacadeRequest request) {
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
            e.printStackTrace();
            throw e;
        }

        return response;
    }

    public SQLResponse update(FacadeRequest request) {
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
            e.printStackTrace();
            throw e;
        }

        return response;
    }
}
