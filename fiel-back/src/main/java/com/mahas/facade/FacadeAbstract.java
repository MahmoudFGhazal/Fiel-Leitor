package com.mahas.facade;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahas.command.post.IPostCommand;
import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.base.address.BaseAddressCommand;
import com.mahas.command.pre.base.address.BaseResidenceTypeCommand;
import com.mahas.command.pre.base.address.BaseStreetTypeCommand;
import com.mahas.command.pre.base.user.BaseGenderCommand;
import com.mahas.command.pre.base.user.BaseUserCommand;
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
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.dto.request.address.ResidenceTypeDTORequest;
import com.mahas.dto.request.address.StreetTypeDTORequest;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.dto.request.user.UserDTORequest;
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
    protected final Map<String, IPreCommand> baseCommands = new HashMap<>();

    @PostConstruct
    public void init() {
        initDaos();
        initDtos();
        initBaseCommands();
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

    public void initBaseCommands() {
        baseCommands.put(UserDTORequest.class.getName(), new BaseUserCommand());
        baseCommands.put(GenderDTORequest.class.getName(), new BaseGenderCommand());
        baseCommands.put(AddressDTORequest.class.getName(), new BaseAddressCommand());
        baseCommands.put(ResidenceTypeDTORequest.class.getName(), new BaseResidenceTypeCommand());
        baseCommands.put(StreetTypeDTORequest.class.getName(), new BaseStreetTypeCommand());
    }

    protected SQLRequest runRulesRequest(FacadeRequest request){
        DTORequest dto = request.getEntity();
        if (dto == null) return null;

        IPreCommand command = request.getPreCommand(); 
        if(command == null) command = baseCommands.get(dto.getClass().getName());

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