package com.mahas.controller;

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

import com.mahas.command.rules.VerifyCreateUser;
import com.mahas.command.rules.VerifyDeleteUser;
import com.mahas.command.rules.VerifyLogin;
import com.mahas.command.rules.VerifyPagination;
import com.mahas.command.rules.VerifyUpdateUser;
import com.mahas.command.rules.VerifyUserExist;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
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

    @Autowired
    VerifyLogin verifyLogin;

    @Autowired
    VerifyUserExist verifyUserExist;

    @PostMapping("/login")
    public ResponseEntity<FacadeResponse> Login(@RequestBody User user) {
        FacadeRequest request = new FacadeRequest();

        User requestUser = new User();
        requestUser.setEmail(user.getEmail());
        requestUser.setPassword(user.getPassword());

        request.setEntity(requestUser);
        request.setLimit(1);
        request.setCommand(verifyLogin);
        
        FacadeResponse response = facade.query(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<FacadeResponse> getUser(
            @RequestParam(value = "userId", required = false) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();
        
        User user = new User();
        if(id != null) {
            user.setId(id);
        }

        request.setEntity(user);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);
        
        if (response.getData().getTotalItem() == 0) {
            response.setMessage("Usuário não encontrado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        if (response.getData().getTotalItem() > 1) {
            response.setMessage("Mais de um usuário encontrado para o ID informado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
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

        request.setCommand(verifyPagination);
        request.setLimit(limit);
        request.setPage(page);
        request.setEntity(new User());

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> createUser(@RequestBody User user) {
        FacadeRequest request = new FacadeRequest();
       
        request.setCommand(verifyCreateUser);
        request.setEntity(user);

        FacadeResponse response = facade.save(request);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<FacadeResponse> deleteUser(
            @RequestParam(value = "userId") Integer id
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setCommand(verifyDeleteUser);

        User user = new User();
        user.setId(id);
        request.setEntity(user);
        
        FacadeResponse response = facade.delete(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<FacadeResponse> updateProfile(@RequestBody User user) {
        FacadeRequest request = new FacadeRequest();

        request.setCommand(verifyUpdateUser);
        
        User requestUser = user;
        requestUser.setEmail(null);
        requestUser.setPassword(null);
        requestUser.setCpf(null);

        request.setEntity(requestUser);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/active")
    public ResponseEntity<FacadeResponse> activeUser(
        @RequestParam(value = "userId", required = true) Integer userId,
        @RequestParam(value = "active", required = true) boolean active
    ) {
        FacadeRequest request = new FacadeRequest();

        request.setCommand(verifyUserExist);
        
        User requestUser = new User();
        requestUser.setId(userId);
        requestUser.setActive(active);

        request.setEntity(requestUser);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }
}
