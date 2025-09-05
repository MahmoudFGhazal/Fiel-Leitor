package com.mahas.facade;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;

public interface IFacade {
    FacadeResponse save(FacadeRequest request);
    FacadeResponse query(FacadeRequest request);
    FacadeResponse update(FacadeRequest request);
    FacadeResponse delete(FacadeRequest request);
}