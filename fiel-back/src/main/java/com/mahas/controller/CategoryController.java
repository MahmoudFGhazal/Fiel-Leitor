package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahas.command.pre.base.product.BaseCategoryCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.product.CategoryDTORequest;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BaseCategoryCommand baseCategoryCommand;

    @GetMapping("/all")
    public ResponseEntity<FacadeResponse> getCategories() {
        FacadeRequest request = new FacadeRequest();

        request.setEntity(new CategoryDTORequest());
        request.setPreCommand(baseCategoryCommand);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }
}
