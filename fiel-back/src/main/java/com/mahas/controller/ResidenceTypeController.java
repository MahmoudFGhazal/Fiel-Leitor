package com.mahas.controller;

import com.mahas.command.pre.base.address.BaseResidenceTypeCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.address.ResidenceTypeDTORequest;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/residenceType")
public class ResidenceTypeController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BaseResidenceTypeCommand baseResidenceTypeCommand;

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getStreetTypes() {
        FacadeRequest request = new FacadeRequest();
        
        request.setEntity(new ResidenceTypeDTORequest());
        request.setPreCommand(baseResidenceTypeCommand);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

}
