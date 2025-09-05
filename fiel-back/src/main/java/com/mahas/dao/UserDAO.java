package com.mahas.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.User;

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
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u where 1=1");
        Map<String, Object> parameters = new HashMap<>();
    
        TypedQuery<User> queryUser = entityManager.createQuery(jpql.toString(), User.class);
        parameters.forEach((queryUser::setParameter));

        return new ArrayList<>(queryUser.getResultList());
    }
}
