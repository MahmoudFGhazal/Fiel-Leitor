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

import com.mahas.command.post.rules.DeleteUserAdapter;
import com.mahas.command.post.rules.LoginAdapter;
import com.mahas.command.post.rules.SetActiveUserAdapter;
import com.mahas.command.post.rules.SignAddressAdapter;
import com.mahas.command.post.rules.SignUserAdapter;
import com.mahas.command.pre.base.user.BaseUserCommand;
import com.mahas.command.pre.rules.VerifyChangePassword;
import com.mahas.command.pre.rules.VerifyCreateAddress;
import com.mahas.command.pre.rules.VerifyCreateUser;
import com.mahas.command.pre.rules.VerifyDeleteUser;
import com.mahas.command.pre.rules.VerifyLogin;
import com.mahas.command.pre.rules.VerifyUpdateUser;
import com.mahas.command.pre.rules.VerifyUserExist;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.dto.request.sign.SignInRequest;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IFacade facade;

    //Pre
    @Autowired
    private VerifyCreateUser verifyCreateUser;

    @Autowired
    private VerifyCreateAddress verifyCreateAddress;

    @Autowired
    private VerifyDeleteUser verifyDeleteUser;

    @Autowired
    private VerifyUpdateUser verifyUpdateUser;

    @Autowired
    private VerifyLogin verifyLogin;

    @Autowired
    private VerifyUserExist verifyUserExist;

    @Autowired
    private VerifyChangePassword verifyChangePassword;

    //Post
    @Autowired
    private BaseUserCommand baseUserCommand;

    @Autowired
    private LoginAdapter loginAdapter;

    @Autowired
    private SignUserAdapter signUserAdapter;

    @Autowired
    private SignAddressAdapter signAddressAdapter;

    @Autowired
    private SetActiveUserAdapter setActiveUserAdapter;

    @Autowired
    private DeleteUserAdapter deleteUserAdapter;

    @PostMapping("/login")
    public ResponseEntity<FacadeResponse> Login(@RequestBody UserDTORequest user) {
        FacadeRequest request = new FacadeRequest();

        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(verifyLogin);
        request.setPostCommand(loginAdapter);
        
        FacadeResponse response = facade.query(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<FacadeResponse> getUser(
            @RequestParam(value = "userId", required = true) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();

        UserDTORequest user = new UserDTORequest();
        user.setId(id);

        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

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

    @PostMapping("/sign")
    public ResponseEntity<FacadeResponse> signIn(@RequestBody SignInRequest body) {
        FacadeRequest userRequest = new FacadeRequest();
       
        userRequest.setPreCommand(verifyCreateUser);
        userRequest.setPostCommand(signUserAdapter);
        userRequest.setEntity(body.getUser());

        FacadeResponse userResponse = facade.save(userRequest);

        DTOResponse dtoResponse = userResponse.getData().getEntity();

        if(dtoResponse == null || !(dtoResponse instanceof UserDTOResponse)) {
            userResponse.setMessage("Erro ao criar usuario");
            userResponse.setTypeResponse(TypeResponse.SERVER_ERROR);
            return ResponseEntity.ok(userResponse);
        }

        UserDTOResponse responseUser = (UserDTOResponse) dtoResponse;
        
        
        body.getAddress().setUser(responseUser.getId());

        FacadeRequest addressRequest = new FacadeRequest();

        addressRequest.setPreCommand(verifyCreateAddress);
        addressRequest.setPostCommand(signAddressAdapter);
        addressRequest.setEntity(body.getAddress());

        FacadeResponse addressResponse = facade.save(addressRequest);
        
        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping
    public ResponseEntity<FacadeResponse> deleteUser(
            @RequestParam(value = "userId", required = true) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();

        UserDTORequest user = new UserDTORequest();
        user.setId(id);
        request.setEntity(user);
        request.setPreCommand(verifyDeleteUser);
        request.setPostCommand(deleteUserAdapter);
        
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
        request.setPostCommand(setActiveUserAdapter);
        
        UserDTORequest user = new UserDTORequest();
        user.setId(userId);
        user.setActive(active);

        request.setEntity(user);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<FacadeResponse> ChangePassword(@RequestBody UserDTORequest user) {
        FacadeRequest request = new FacadeRequest();

        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(verifyChangePassword);
        
        FacadeResponse response = facade.update(request);

        return ResponseEntity.ok(response);
    }
}
