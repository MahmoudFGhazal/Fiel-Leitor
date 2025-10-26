package com.mahas.controller;

import java.util.ArrayList;
import java.util.List;

import com.mahas.command.post.adapters.ActiveBookAdapter;
import com.mahas.command.post.adapters.GetBookAdapter;
import com.mahas.command.post.adapters.GetBookForSaleAdapter;
import com.mahas.command.pre.base.product.BaseBookCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.TypeResponse;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private GetBookAdapter getBookAdapter;

    @Autowired
    private ActiveBookAdapter activeBookAdapter;

    @Autowired
    private GetBookForSaleAdapter getBookForSaleAdapter;

    @GetMapping
    public ResponseEntity<FacadeResponse> getBook(
            @RequestParam(value = "bookId", required = true) Integer id
        ) {
        FacadeRequest request = new FacadeRequest();

        BookDTORequest book = new BookDTORequest();
        book.setId(id);

        request.setEntity(book);
        request.setLimit(1);
        request.setPreCommand(baseBookCommand);
        request.setPostCommand(getBookAdapter);

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

    @GetMapping("/active")
    public ResponseEntity<FacadeResponse> getBooksActive() {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseBookCommand);
        request.setPostCommand(getBookAdapter);
        BookDTORequest book = new BookDTORequest();
        book.setActive(true);
        request.setEntity(book); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getBooks() {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseBookCommand);
        request.setPostCommand(getBookAdapter);
        BookDTORequest book = new BookDTORequest();
        request.setEntity(book); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<FacadeResponse> getBookForSale(
            @RequestParam(value = "bookIds", required = true) Integer[] ids
        ) {
        FacadeResponse listResponse = new FacadeResponse();
        List<DTOResponse> entities = new ArrayList<>();

        for(Integer id : ids) {
            FacadeRequest request = new FacadeRequest();

            BookDTORequest book = new BookDTORequest();
            book.setId(id);

            request.setEntity(book);
            request.setLimit(1);
            request.setPreCommand(baseBookCommand);
            request.setPostCommand(getBookForSaleAdapter);

            FacadeResponse response = facade.query(request);
            
            if (response == null || response.getData() == null || response.getData().getEntity() == null) {
                continue;
            }

            entities.add(response.getData().getEntity());
        }

        DataResponse data = new DataResponse();
        data.setEntities(entities);

        listResponse.setData(data);

        return ResponseEntity.ok(listResponse);
    }

    @PutMapping("/active")
    public ResponseEntity<FacadeResponse> setBookActive(
        @RequestParam(value = "bookId", required = true) Integer id,
        @RequestParam(value = "active", required = true) Boolean active
    ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseBookCommand);
        request.setPostCommand(activeBookAdapter);
        BookDTORequest book = new BookDTORequest();
        book.setId(id);
        book.setActive(active);

        request.setEntity(book); 

        FacadeResponse response = facade.update(request);
        
        return ResponseEntity.ok(response);
    }

}
