package com.mahas.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahas.command.post.IPostCommand;
import com.mahas.command.pre.IPreCommand;
import com.mahas.dao.IDAO;
import com.mahas.dao.address.AddressDAO;
import com.mahas.dao.address.ResidenceTypeDAO;
import com.mahas.dao.address.StreetTypeDAO;
import com.mahas.dao.user.GenderDAO;
import com.mahas.dao.user.UserDAO;
import com.mahas.domain.DataResponse;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.address.ResidenceType;
import com.mahas.domain.address.StreetType;
import com.mahas.domain.user.Gender;
import com.mahas.domain.user.User;

import jakarta.annotation.PostConstruct;

public abstract class FacadeAbstract {

    //User
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenderDAO genderDAO;

    //Address
    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private ResidenceTypeDAO residenceTypeDAO;

    @Autowired
    private StreetTypeDAO streetTypeDAO;

    protected final Map<String, IDAO> daos = new HashMap<>();

    @PostConstruct
    public void initDaos() {
        daos.put(User.class.getName(), userDAO);
        daos.put(Gender.class.getName(), genderDAO);
        daos.put(Address.class.getName(), addressDAO);
        daos.put(ResidenceType.class.getName(), residenceTypeDAO);
        daos.put(StreetType.class.getName(), streetTypeDAO);
    }

    protected SQLRequest runRulesRequest(FacadeRequest request){
        IPreCommand command = request.getPreCommand(); 
        if(command == null) return null;
        
        return command.execute(request);
    }

    protected DataResponse runRulesResponse(FacadeRequest request, SQLResponse SQLResponse){
        IPostCommand command = request.getPostCommand(); 
        
        DataResponse data;
            if (command == null) {
            // Cria um DataResponse vazio
            data = new DataResponse();
            data.setEntities(List.of()); 
            data.setPage(request.getPage() > 0 ? request.getPage() : 1);
            data.setLimit(request.getLimit() > 0 ? request.getLimit() : 0);
            data.setPageCount(0);
            data.setTotalItem(0);
            data.setTotalPage(0);
            return data;
        }

        data = command.execute(SQLResponse);
        
        return data;
    }
}