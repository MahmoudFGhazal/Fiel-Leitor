package com.mahas.dao;

import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;

public interface IDAO {
    default public SQLResponse save(SQLRequest request) {
        return null;
    }

    default public SQLResponse delete(SQLRequest request) {
        return null;
    }

    default public SQLResponse update(SQLRequest request) {
        return null;
    }

    default public SQLResponse query(SQLRequest request) {
        return null;
    }
}