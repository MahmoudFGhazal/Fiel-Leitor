package com.mahas.dao.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Component
public class UserDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<DomainEntity> query(DomainEntity entity) {
        if(!(entity instanceof User)){
            return List.of();
        }

        User user = (User) entity;
        StringBuilder jpql = new StringBuilder("SELECT u FROM users u where 1=1");
        Map<String, Object> parameters = new HashMap<>();

        if (user.getId() != null) {
            jpql.append(" AND u.id = :id");
            parameters.put("id", user.getId());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            jpql.append(" AND LOWER(u.name) LIKE LOWER(:name)");
            parameters.put("name", "%" + user.getName() + "%");
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            jpql.append(" AND LOWER(u.email) = LOWER(:email)");
            parameters.put("email", user.getEmail());
        }
        if (user.getActive() != null) {
            jpql.append(" AND u.active = :active");
            parameters.put("active", user.getActive());
        }
    
        TypedQuery<User> queryUser = entityManager.createQuery(jpql.toString(), User.class);
        parameters.forEach((queryUser::setParameter));

        return new ArrayList<>(queryUser.getResultList());
    }
}
