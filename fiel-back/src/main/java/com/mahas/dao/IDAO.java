package com.mahas.dao;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLResponse;

public interface IDAO {
    default public SQLResponse save(FacadeRequest request) {
        return null;
    }

    default public SQLResponse delete(FacadeRequest request) {
        return null;
    }

    default public SQLResponse update(FacadeRequest request) {
        return null;
    }

    default public SQLResponse query(FacadeRequest request) {
        return null;
    }
}