package com.mahas.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mahas.dao.IDAO;
import com.mahas.domain.DataResponse;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.dto.request.DTORequest;

@Component
public class Facade extends FacadeAbstract implements IFacade {

    @Transactional
    @Override
    public FacadeResponse save(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();
        
        DTORequest entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLRequest sqlRequest = runRulesRequest(request);
        
        if(sqlRequest == null) {
            response.setMessage("Command não especificado");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = sqlRequest.getEntity().getClass().getName();

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.save(sqlRequest);
        if(result == null) {
            response.setMessage("Erro ao fazer o insert no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        DataResponse data = runRulesResponse(request, result);

        response.setData(data);
        
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse query(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();

        DTORequest entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLRequest sqlRequest = runRulesRequest(request);
        
        if(sqlRequest == null) {
            response.setMessage("Command não especificado");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = sqlRequest.getEntity().getClass().getName();

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.query(sqlRequest);
        if(result == null) {
            response.setMessage("Erro ao fazer a query no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        DataResponse data = runRulesResponse(request, result);

        response.setData(data);

        return response;
    }

    @Transactional
    @Override
    public FacadeResponse update(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();

        DTORequest entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLRequest sqlRequest = runRulesRequest(request);

        if(sqlRequest == null) {
            response.setMessage("Command não especificado");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = sqlRequest.getEntity().getClass().getName();

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.update(sqlRequest);

        if(result == null) {
            response.setMessage("Erro ao fazer a query no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        DataResponse data = runRulesResponse(request, result);

        response.setData(data);
        
        return response;
    }

    @Transactional
    @Override
    public FacadeResponse delete(FacadeRequest request) {
        FacadeResponse response = new FacadeResponse();

        DTORequest entity = request.getEntity();
        if(entity == null) {
            response.setMessage(entity + " não é uma entidade");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLRequest sqlRequest = runRulesRequest(request);
        
        if(sqlRequest == null) {
            response.setMessage("Command não especificado");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        String nameEntity = sqlRequest.getEntity().getClass().getName();

        IDAO dao = daos.get(nameEntity);
        if(dao == null) {
            response.setMessage(nameEntity + " não encontrado o dao");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        SQLResponse result = dao.delete(sqlRequest);
        if(result == null) {
            response.setMessage("Erro ao fazer a query no banco");
            response.setTypeResponse(TypeResponse.SERVER_ERROR);
            return response;
        }

        DataResponse data = runRulesResponse(request, result);

        response.setData(data);
        
        return response;
    }
}