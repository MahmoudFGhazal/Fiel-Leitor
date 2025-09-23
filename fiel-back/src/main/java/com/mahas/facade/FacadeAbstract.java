package com.mahas.facade;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.address.ResidenceType;
import com.mahas.domain.address.StreetType;
import com.mahas.domain.user.Gender;
import com.mahas.domain.user.User;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.dto.response.address.ResidenceTypeDTOResponse;
import com.mahas.dto.response.address.StreetTypeDTOResponse;
import com.mahas.dto.response.user.GenderDTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;

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
    protected final Map<String, Class<? extends DTOResponse>> dtos = new HashMap<>();

    @PostConstruct
    public void init() {
        initDaos();
        initDtos();
    }

    public void initDaos() {
        daos.put(User.class.getName(), userDAO);
        daos.put(Gender.class.getName(), genderDAO);
        daos.put(Address.class.getName(), addressDAO);
        daos.put(ResidenceType.class.getName(), residenceTypeDAO);
        daos.put(StreetType.class.getName(), streetTypeDAO);
    }

    
    public void initDtos() {
        dtos.put(User.class.getName(), UserDTOResponse.class);
        dtos.put(Gender.class.getName(), GenderDTOResponse.class);
        dtos.put(Address.class.getName(), AddressDTOResponse.class);
        dtos.put(ResidenceType.class.getName(), ResidenceTypeDTOResponse.class);
        dtos.put(StreetType.class.getName(), StreetTypeDTOResponse.class);
    }

    protected SQLRequest runRulesRequest(FacadeRequest request){
        IPreCommand command = request.getPreCommand(); 
        if(command == null) return null;
        
        return command.execute(request);
    }

    protected DataResponse runRulesResponse(FacadeRequest request, SQLResponse sqlResponse){
        IPostCommand command = request.getPostCommand(); 
        if (command != null) return command.execute(sqlResponse);
        
        return executeDefault(sqlResponse);
    }

    private DataResponse executeDefault(SQLResponse sqlResponse) {
        DataResponse response = new DataResponse();

        DomainEntity entity = sqlResponse.getEntity();
        if (entity != null) {
            DTOResponse dto = createDTOFromEntity(entity);
            if (dto != null) {
                response.setEntity(dto);
            }
        }

        return response;
    }

    private DTOResponse createDTOFromEntity(DomainEntity entity) {
        Class<? extends DTOResponse> dtoClass = dtos.get(entity.getClass().getName());
        if (dtoClass != null) {
            try {
                DTOResponse dto = dtoClass.getDeclaredConstructor().newInstance();
                
                dto.mapFromEntity(entity);
                return dto;
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            }
        }
        return null;
    }
}