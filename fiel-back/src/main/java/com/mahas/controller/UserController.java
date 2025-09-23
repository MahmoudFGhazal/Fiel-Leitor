package com.mahas.controller;

import com.mahas.command.pre.base.user.BaseUserCommand;
import com.mahas.command.pre.rules.VerifyCreateUser;
import com.mahas.command.pre.rules.VerifyDeleteUser;
import com.mahas.command.pre.rules.VerifyLogin;
import com.mahas.command.pre.rules.VerifyUpdateUser;
import com.mahas.command.pre.rules.VerifyUserExist;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BaseUserCommand baseUserCommand;

    @Autowired
    VerifyCreateUser verifyCreateUser;

    @Autowired
    VerifyDeleteUser verifyDeleteUser;

    @Autowired
    VerifyUpdateUser verifyUpdateUser;

    @Autowired
    VerifyLogin verifyLogin;

    @Autowired
    VerifyUserExist verifyUserExist;

    @PostMapping("/login")
    public ResponseEntity<FacadeResponse> Login(@RequestBody UserDTORequest user) {
        FacadeRequest request = new FacadeRequest();

        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(verifyLogin);
        
        FacadeResponse response = facade.query(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<FacadeResponse> getUser(
            @RequestParam(value = "userId", required = false) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();

        UserDTORequest user = new UserDTORequest();
        if(id != null) {
            user.setId(id);
        }

        request.setEntity(user);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);
        
        if (response.getData().getEntity() == null) {
            response.setMessage("Usuário não encontrado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();
        System.out.println(limit);
        request.setLimit(limit);
        request.setPage(page);
        request.setPreCommand(baseUserCommand);
        request.setEntity(new UserDTORequest());

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> createUser(@RequestBody UserDTORequest user) {
        FacadeRequest request = new FacadeRequest();
       
        request.setPreCommand(verifyCreateUser);
        request.setEntity(user);

        FacadeResponse response = facade.save(request);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<FacadeResponse> deleteUser(
            @RequestParam(value = "userId") Integer id
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyDeleteUser);

        UserDTORequest user = new UserDTORequest();
        user.setId(id);
        request.setEntity(user);
        
        FacadeResponse response = facade.delete(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<FacadeResponse> updateProfile(@RequestBody UserDTORequest user) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyUpdateUser);

        request.setEntity(user);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/active")
    public ResponseEntity<FacadeResponse> activeUser(
        @RequestParam(value = "userId", required = true) Integer userId,
        @RequestParam(value = "active", required = true) boolean active
    ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyUserExist);
        
        UserDTORequest user = new UserDTORequest();
        user.setId(userId);
        user.setActive(active);

        request.setEntity(user);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }
}
