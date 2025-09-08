package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.ICommand;
import com.mahas.command.rules.VerifyCreateUser;
import com.mahas.command.rules.VerifyDeleteUser;
import com.mahas.command.rules.VerifyPagination;
import com.mahas.command.rules.VerifyUpdateUser;
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

    @Autowired
    VerifyPagination verifyPagination;

    @Autowired
    VerifyCreateUser verifyCreateUser;

    @Autowired
    VerifyDeleteUser verifyDeleteUser;

    @Autowired
    VerifyUpdateUser verifyUpdateUser;

    @GetMapping
    public ResponseEntity<FacadeResponse> getUser(
            @RequestParam(value = "id", required = true) Long id
        ) {
        FacadeRequest request = new FacadeRequest();
        
        User user = new User();
        user.setId(id);

        request.setEntity(user);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        ICommand[] commands = new ICommand[]{verifyPagination};
        request.setCommands(commands);
        request.setLimit(limit);
        request.setPage(page);
        request.setEntity(new User());

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> createUser(@RequestBody User user) {
        FacadeRequest request = new FacadeRequest();
       
        ICommand[] commands = new ICommand[]{verifyCreateUser};
        request.setCommands(commands);
        request.setEntity(user);

        FacadeResponse response = facade.save(request);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<FacadeResponse> deleteUser(
            @RequestParam(value = "userId") Long userId
        ) {
        FacadeRequest request = new FacadeRequest();

        ICommand[] commands = new ICommand[]{verifyDeleteUser};
        request.setCommands(commands);

        User user = new User();
        user.setId(userId);
        request.setEntity(user);
        
        FacadeResponse response = facade.delete(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<FacadeResponse> updateProfile(@RequestBody User user) {
        FacadeRequest request = new FacadeRequest();

        ICommand[] commands = new ICommand[]{verifyUpdateUser};
        request.setCommands(commands);
        
        user.setEmail(null);
        user.setPassword(null);
        user.setCpf(null);

        request.setEntity(user);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }

}
