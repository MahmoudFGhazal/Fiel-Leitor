package com.mahas.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mahas.dao.IDAO;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.TypeResponse;

@Component
public class Facade extends FacadeAbstract implements IFacade {

    @Transactional
    @Override
    public FacadeResponse save(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();
        DomainEntity entity = request.getEntity();

        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = entity.getClass().getName();

        IDAO dao = daos.get(nameEntity);
        
        String error = runRules(request);

        if(error != null) {
            response.setTypeResponse(TypeResponse.CLIENT_ERROR);
            response.setMessage(error);
            return response;
        }

        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            return response;
        }

        SQLResponse result = dao.save(request);

        if(result == null) {
            response.setMessage("Erro ao fazer o insert no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        response.setData(result);
        
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse query(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();

        DomainEntity entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = entity.getClass().getName();

        String error = runRules(request);

        if(error != null) {
            response.setTypeResponse(TypeResponse.CLIENT_ERROR);
            response.setMessage(error);
            return response;
        }

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.query(request);
        if(result == null) {
            response.setMessage("Erro ao fazer a query no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        response.setData(result);
        
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse update(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();

        DomainEntity entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = entity.getClass().getName();

        String error = runRules(request);
        if(error != null) {
            response.setTypeResponse(TypeResponse.CLIENT_ERROR);
            response.setMessage(error);
            return response;
        }

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.update(request);
        if(result == null) {
            response.setMessage("Erro ao fazer a query no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        response.setData(result);
        
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse delete(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();

        DomainEntity entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = entity.getClass().getName();

        String error = runRules(request);
        if(error != null) {
            response.setTypeResponse(TypeResponse.CLIENT_ERROR);
            response.setMessage(error);
            return response;
        }

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.delete(request);
        if(result == null) {
            response.setMessage("Erro ao fazer a query no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        response.setData(result);
        
        return response;
    }
}