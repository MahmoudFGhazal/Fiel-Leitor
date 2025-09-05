package com.mahas.dao;

import java.util.Collections;
import java.util.List;

import com.mahas.domain.DomainEntity;

public interface IDAO {
    default public DomainEntity save(DomainEntity entity) {
        return null;
    }

    default public DomainEntity delete(DomainEntity entity) {
        return null;
    }

    default public DomainEntity update(DomainEntity entity) {
        return null;
    }

    default public List<DomainEntity> query(DomainEntity entity) {
        return Collections.emptyList();
    }
}