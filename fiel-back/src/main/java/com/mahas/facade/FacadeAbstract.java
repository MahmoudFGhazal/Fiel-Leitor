package com.mahas.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahas.command.Command;
import com.mahas.command.ICommand;
import com.mahas.dao.IDAO;
import com.mahas.dao.address.AddressDAO;
import com.mahas.dao.address.ResidenceTypeDAO;
import com.mahas.dao.address.StreetTypeDAO;
import com.mahas.dao.user.GenderDAO;
import com.mahas.dao.user.UserDAO;
import com.mahas.domain.FacadeRequest;
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

    protected String runRules(FacadeRequest request){
        ICommand[] commands = request.getCommands(); 
        if(commands == null || commands.length == 0) return null;
        
        for(ICommand c : commands){
            Command command = new Command();
            command.setCommand(c);
            String response = command.execute(request);
            if(response != null) {
                return response;
            }
        }

        return null;
    }
}