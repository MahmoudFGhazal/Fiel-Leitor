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
            whereClause.append(" AND id = :id");
            parameters.put("id", user.getId());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            whereClause.append(" AND unaccent(LOWER(name)) LIKE unaccent(LOWER(:name))");
            parameters.put("name", "%" + user.getName() + "%");
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            whereClause.append(" AND LOWER(email) = LOWER(:email)");
            parameters.put("email", user.getEmail());
        }
        if (user.getActive() != null) {
            whereClause.append(" AND active = :active");
            parameters.put("active", user.getActive());
        }

        sql.append(whereClause);
        countSql.append(whereClause);

        Integer page = request.getPage() != null ? request.getPage() : 1;
        Integer limit = request.getLimit() != null ? request.getLimit() : 10;
        int offset = (page - 1) * limit;

        sql.append(" LIMIT :limit OFFSET :offset");

        try {
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

        } catch (PersistenceException e) {
            e.printStackTrace();

            response.setEntities(null);
            response.setEntity(null);
            response.setPage(0);
            response.setLimit(0);
            response.setTotalPage(0);
            response.setPageCount(0);
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
        String sql = "INSERT INTO users (usr_email, usr_password, usr_name, usr_cpf, usr_gen_id, usr_birthday, usr_phone_number, usr_created_at, usr_updated_at, usr_published_at) " +
                 "VALUES (:email, :password, :name, :cpf, :genId, :birthday, :phoneNumber, :createdAt, :updatedAt, :publishedAt);";
        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());
        query.setParameter("name", user.getName());
        query.setParameter("cpf", user.getCpf());
        query.setParameter("genId", user.getGenId());
        query.setParameter("birthday", user.getBirthday());
        query.setParameter("phoneNumber", user.getPhoneNumber());
        query.setParameter("createdAt", user.getCreatedAt());
        query.setParameter("updatedAt", user.getUpdatedAt());
        query.setParameter("publishedAt", user.getPublishedAt());

        try {
            int result = query.executeUpdate();
            if (result > 0) {
                response.setEntity(user);
            } else {
                response.setEntity(null);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            response.setEntity(null);
        }

        return response;
    }
}
