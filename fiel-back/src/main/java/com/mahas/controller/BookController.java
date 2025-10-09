package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.pre.base.product.BaseBookCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.facade.IFacade;

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
    public ResponseEntity<FacadeResponse> getBook(
            @RequestParam(value = "bookId", required = true) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();
        System.out.println(id);
        BookDTORequest book = new BookDTORequest();
        book.setId(id);

        request.setEntity(book);
        request.setLimit(1);
        request.setPreCommand(baseBookCommand);

        FacadeResponse response = facade.query(request);
        
        if (response.getData().getEntity() == null) {
            response.setMessage("Livro não encontrado");
            response.setTypeResponse(TypeResponse.CONFLICT);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getBooks() {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseBookCommand);
        BookDTORequest book = new BookDTORequest();
        request.setEntity(book); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
