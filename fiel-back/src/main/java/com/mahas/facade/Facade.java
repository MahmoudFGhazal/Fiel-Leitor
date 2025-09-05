package com.mahas.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;

@Component
public class Facade extends FacadeAbstract implements IFacade {

    @Transactional
    @Override
    public FacadeResponse save(FacadeRequest request) {
        DomainEntity entity = request.getEntity();

        String nameEntity = entity.getClass().getName();

        IDAO dao = daos.get(nameEntity);
        FacadeResponse response = new FacadeResponse();
        
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            return response;
        }

        DomainEntity result = dao.save(entity);

        if(result == null) {
            response.setMessage("Erro ao fazer o insert no banco");
            return response;
        }
        
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse query(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse update(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse delete(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();
        return response;
    }
}