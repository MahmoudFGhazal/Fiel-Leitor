package com.mahas.controller;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/gender")
public class GenderController {
    @Autowired
    private IFacade facade;

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getGenders(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setEntity(new GenderDTORequest());
        request.setLimit(limit);
        request.setPage(page);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
