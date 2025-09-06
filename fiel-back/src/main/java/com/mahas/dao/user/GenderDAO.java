package com.mahas.dao.user;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class GenderDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

}
