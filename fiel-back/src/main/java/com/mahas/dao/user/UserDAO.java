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
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM users WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();
        StringBuilder whereClause = new StringBuilder();

        if (user.getId() != null) {
            whereClause.append(" AND usr_id = :id");
            parameters.put("id", user.getId());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            whereClause.append(" AND LOWER(usr_name) LIKE LOWER(:name)");
            parameters.put("name", "%" + user.getName() + "%");
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            whereClause.append(" AND LOWER(usr_email) = LOWER(:email)");
            parameters.put("email", user.getEmail());
        }
        if (user.getActive() != null) {
            whereClause.append(" AND usr_active = :active");
            parameters.put("active", user.getActive());
        }

        sql.append(whereClause);
        countSql.append(whereClause);

        Integer page = request.getPage() != null ? request.getPage() : 1;
        Integer limit = request.getLimit() != null ? request.getLimit() : 10;
        int offset = (page - 1) * limit;

        sql.append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);

        try {
            Query nativeQuery = entityManager.createNativeQuery(sql.toString(), User.class);
            parameters.forEach(nativeQuery::setParameter);

            @SuppressWarnings("unchecked")
            List<User> resultList = nativeQuery.getResultList();

            Query countQuery = entityManager.createNativeQuery(countSql.toString());
            parameters.forEach(countQuery::setParameter);
            Number totalCount = ((Number) countQuery.getSingleResult());

            int totalItems = totalCount.intValue();
            int totalPage = (int) Math.ceil((double) totalItems / limit);
            int pageCount = (int) Math.ceil((double) totalItems / limit);

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

    public SQLResponse save(FacadeRequest request) {
        SQLResponse response = new SQLResponse();

        DomainEntity entity = request.getEntity();
        if(!(entity instanceof User)){
            return null;
        }

        User user = (User) entity;
        String sql = "INSERT INTO users (usr_email, usr_password, usr_name, usr_cpf, usr_gen_id, usr_birthday, usr_phone_number) " +
                 "VALUES (:email, :password, :name, :cpf, :genId, :birthday, :phoneNumber);";
        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());
        query.setParameter("name", user.getName());
        query.setParameter("cpf", user.getCpf());
        query.setParameter("genId", user.getGender().getId());
        query.setParameter("birthday", user.getBirthday());
        query.setParameter("phoneNumber", user.getPhoneNumber());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(user);
            } else {
                response.setEntity(null);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        }

        return response;
    }
}
