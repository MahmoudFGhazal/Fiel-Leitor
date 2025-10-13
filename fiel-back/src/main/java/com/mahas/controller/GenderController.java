package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahas.command.pre.base.user.BaseGenderCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/gender")
public class GenderController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BaseGenderCommand baseGenderCommand;

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getGenders() {
        FacadeRequest request = new FacadeRequest();

        request.setEntity(new GenderDTORequest());
        request.setPreCommand(baseGenderCommand);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
