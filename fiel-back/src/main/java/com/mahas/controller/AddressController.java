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

import com.mahas.command.rules.VerifyCreateAddress;
import com.mahas.command.rules.VerifyDeleteAddress;
import com.mahas.command.rules.VerifyGetUserByUser;
import com.mahas.command.rules.VerifyUpdateAddress;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.user.User;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private IFacade facade;

    @Autowired
    private VerifyCreateAddress verifyCreateAddress;

    @Autowired
    private VerifyDeleteAddress verifyDeleteAddress;

    @Autowired
    private VerifyUpdateAddress verifyUpdateAddress;

    @Autowired
    private VerifyGetUserByUser verifyGetUserByUser;

    @GetMapping
    public ResponseEntity<FacadeResponse> getAddress(
            @RequestParam(value = "addressId", required = false) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();
        
        Address address = new Address();
        if(id != null) {
            address.setId(id);
        }

        request.setEntity(address);
        FacadeResponse response = facade.query(request);
        
        if (response.getData().getTotalItem() == 0) {
            response.setMessage("Endereço não encontrado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        if (response.getData().getTotalItem() > 1) {
            response.setMessage("Mais de um endereço encontrado para o ID informado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<FacadeResponse> getAddressesByUser(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setCommand(verifyGetUserByUser);
        request.setLimit(limit);
        request.setPage(page);
        Address address = new Address();
        User user = new User();
        user.setId(userId);
        address.setUser(user);
        request.setEntity(address); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> createAddress(@RequestBody Address address) {
        FacadeRequest request = new FacadeRequest();

        request.setCommand(verifyCreateAddress);
        address.setId(null);
        request.setEntity(address);
        FacadeResponse response = facade.save(request);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<FacadeResponse> deleteAddress(
        @RequestParam(value = "addressId", required = false) Integer id
    ) {
        FacadeRequest request = new FacadeRequest();

        Address address = new Address();
        address.setId(id);
        request.setEntity(address);
        request.setCommand(verifyDeleteAddress);
        
        FacadeResponse response = facade.delete(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<FacadeResponse> updateAddress(@RequestBody Address address) {
        FacadeRequest request = new FacadeRequest();

        request.setCommand(verifyUpdateAddress);
        request.setEntity(address);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }
}
