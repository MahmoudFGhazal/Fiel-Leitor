package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.ICommand;
import com.mahas.command.rules.VerifyCreateUser;
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
    public ResponseEntity<FacadeResponse> getAllUsers(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "limit") Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        VerifyPagination verifyPagination = new VerifyPagination();

        ICommand[] commands = new ICommand[]{verifyPagination};
        request.setCommands(commands);
        request.setLimit(limit);
        request.setPage(page);
        request.setEntity(new User());

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> createUser() {
        FacadeRequest request = new FacadeRequest();

        VerifyCreateUser verifyCreateUser = new VerifyCreateUser();

        ICommand[] commands = new ICommand[]{verifyCreateUser};
        request.setCommands(commands);
        request.setEntity(new User());

        FacadeResponse response = facade.save(request);
        
        return ResponseEntity.ok(response);
    }
}
