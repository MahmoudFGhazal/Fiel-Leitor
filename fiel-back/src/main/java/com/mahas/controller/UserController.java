package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahas.command.ICommand;
import com.mahas.command.rules.VerifyPagination;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.User;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IFacade facade;

    @GetMapping
    public ResponseEntity<FacadeResponse> getAllClients() {
        FacadeRequest request = new FacadeRequest();

        VerifyPagination verifyPagination = new VerifyPagination();

        ICommand[] commands = new ICommand[]{verifyPagination};
        request.setCommands(commands);
        request.setLimit(10);
        request.setPage(1);
        request.setEntity(new User());

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
