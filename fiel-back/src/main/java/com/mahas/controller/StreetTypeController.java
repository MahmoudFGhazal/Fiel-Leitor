package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.StreetType;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/streetType")
public class StreetTypeController {
    @Autowired
    private IFacade facade;

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getStreetTypes() {
        FacadeRequest request = new FacadeRequest();
        
        request.setEntity(new StreetType());

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

}
