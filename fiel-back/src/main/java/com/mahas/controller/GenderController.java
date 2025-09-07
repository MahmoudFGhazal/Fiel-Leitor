package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.ICommand;
import com.mahas.command.rules.VerifyPagination;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Gender;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/gender")
public class GenderController {
    @Autowired
    private IFacade facade;

    @Autowired
    VerifyPagination verifyPagination;

    @GetMapping
    public ResponseEntity<FacadeResponse> getAllGenders(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        ICommand[] commands = new ICommand[]{verifyPagination};
        request.setCommands(commands);
        request.setEntity(new Gender());
        request.setLimit(limit);
        request.setPage(page);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
