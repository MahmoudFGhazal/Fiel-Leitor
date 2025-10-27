package com.mahas.controller;

import com.mahas.command.post.adapters.CreateAddressAdapter;
import com.mahas.command.post.adapters.DeleteAddressAdapter;
import com.mahas.command.pre.base.address.BaseAddressCommand;
import com.mahas.command.pre.rules.VerifyCreateAddress;
import com.mahas.command.pre.rules.VerifyDeleteAddress;
import com.mahas.command.pre.rules.VerifyGetAddressByUser;
import com.mahas.command.pre.rules.VerifyUpdateAddress;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private VerifyGetAddressByUser verifyGetAddressByUser;

    //Post
    @Autowired
    private BaseAddressCommand baseAddressCommand;
    
    @Autowired
    private CreateAddressAdapter createAddressAdapter;

    @Autowired
    private DeleteAddressAdapter deleteAddressAdapter;

    @GetMapping
    public ResponseEntity<FacadeResponse> getAddress(
            @RequestParam(value = "addressId", required = true) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();
        
        AddressDTORequest address = new AddressDTORequest();
        address.setId(id);
        
        request.setEntity(address);
        request.setPreCommand(baseAddressCommand);
        
        FacadeResponse response = facade.query(request);
        
        if(response.getData() == null || response.getData().getTotalItem() == 0) {
            response.setMessage("Endereço não encontrado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        if (response.getData() == null || response.getData().getTotalItem() > 1) {
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
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyGetAddressByUser);
        request.setLimit(limit);
        request.setPage(page);
        AddressDTORequest address = new AddressDTORequest();
        address.setUser(userId);
        request.setEntity(address); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> createAddress(@RequestBody AddressDTORequest address) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyCreateAddress);
        request.setPostCommand(createAddressAdapter);
        address.setId(null);
        request.setEntity(address);
        FacadeResponse response = facade.save(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delete")
    public ResponseEntity<FacadeResponse> deleteAddress(
        @RequestParam(value = "addressId", required = true) Integer id
    ) {
        FacadeRequest request = new FacadeRequest();

        AddressDTORequest address = new AddressDTORequest();
        address.setId(id);
        request.setEntity(address);
        request.setPreCommand(verifyDeleteAddress);
        request.setPostCommand(deleteAddressAdapter);
        
        FacadeResponse response = facade.delete(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<FacadeResponse> updateAddress(@RequestBody AddressDTORequest address) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyUpdateAddress);
        request.setPostCommand(createAddressAdapter);
        request.setEntity(address);
        
        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }
}
