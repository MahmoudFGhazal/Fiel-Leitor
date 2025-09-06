package com.mahas.dao.address;

import org.springframework.stereotype.Component;

import com.mahas.dao.IDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class CountryDAO implements IDAO {
    @PersistenceContext
    private EntityManager entityManager;

}
