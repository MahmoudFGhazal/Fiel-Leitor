package com.mahas.controller;

import com.mahas.command.pre.base.product.BaseBookCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/book")
public class BookController {
    @Autowired
    private IFacade facade;

    //Pre
    @Autowired
    private BaseBookCommand baseBookCommand;

    //Post


    @GetMapping
    public ResponseEntity<FacadeResponse> getBooks() {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseBookCommand);
        BookDTORequest book = new BookDTORequest();
        request.setEntity(book); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
