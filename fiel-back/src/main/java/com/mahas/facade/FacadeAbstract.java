package com.mahas.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahas.command.Command;
import com.mahas.command.ICommand;
import com.mahas.dao.IDAO;
import com.mahas.dao.address.AddressDAO;
import com.mahas.dao.address.AddressNameDAO;
import com.mahas.dao.address.CityDAO;
import com.mahas.dao.address.CountryDAO;
import com.mahas.dao.address.ResidenceTypeDAO;
import com.mahas.dao.address.StateDAO;
import com.mahas.dao.address.StreetDAO;
import com.mahas.dao.address.StreetTypeDAO;
import com.mahas.dao.user.GenderDAO;
import com.mahas.dao.user.UserDAO;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.address.Address;
import com.mahas.domain.address.AddressName;
import com.mahas.domain.address.City;
import com.mahas.domain.address.Country;
import com.mahas.domain.address.ResidenceType;
import com.mahas.domain.address.State;
import com.mahas.domain.address.Street;
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
    private AddressNameDAO addressNameDAO;

    @Autowired
    private CityDAO cityDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private ResidenceTypeDAO residenceTypeDAO;

    @Autowired
    private StateDAO stateDAO;

    @Autowired
    private StreetDAO streetDAO;

    @Autowired
    private StreetTypeDAO streetTypeDAO;

    protected final Map<String, IDAO> daos = new HashMap<>();

    @PostConstruct
    public void initDaos() {
        daos.put(User.class.getName(), userDAO);
        daos.put(Gender.class.getName(), genderDAO);
        daos.put(Address.class.getName(), addressDAO);
        daos.put(AddressName.class.getName(), addressNameDAO);
        daos.put(City.class.getName(), cityDAO);
        daos.put(Country.class.getName(), countryDAO);
        daos.put(ResidenceType.class.getName(), residenceTypeDAO);
        daos.put(State.class.getName(), stateDAO);
        daos.put(Street.class.getName(), streetDAO);
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