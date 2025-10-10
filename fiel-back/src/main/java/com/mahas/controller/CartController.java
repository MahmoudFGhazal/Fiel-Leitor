package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.pre.base.product.BaseCartCommand;
import com.mahas.command.pre.rules.VerifyCreateCart;
import com.mahas.command.pre.rules.VerifyDeleteCart;
import com.mahas.command.pre.rules.VerifyUpdateCart;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.product.CartDTORequest;
import com.mahas.dto.request.product.cart.UpdateCartRequest;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private IFacade facade;

    //Pre
    @Autowired
    private BaseCartCommand baseCartCommand;

    @Autowired
    private VerifyCreateCart createCardCommand;

    @Autowired
    private VerifyUpdateCart updateCardCommand;

    @Autowired
    private VerifyDeleteCart deleteCardCommand;

    //Post


    @GetMapping
    public ResponseEntity<FacadeResponse> getCart(
            @RequestParam(value = "userId", required = true) Integer userId
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseCartCommand);
        CartDTORequest cart = new CartDTORequest();
        cart.setUser(userId);
        request.setEntity(cart); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> updateCart(@RequestBody UpdateCartRequest body) {
        FacadeResponse lastResponse = new FacadeResponse();
        System.out.println(body.getAddIds().length);
        if (body.getAddIds() != null) {
            for (CartDTORequest piece : body.getAddIds()) {
                if (piece == null) continue;

                CartDTORequest dto = new CartDTORequest();
                dto.setUser(body.getUserId());
                dto.setBook(piece.getBook()); 
                dto.setQuantity(piece.getQuantity());

                FacadeRequest req = new FacadeRequest();
                req.setPreCommand(createCardCommand); 
                req.setEntity(dto);

                lastResponse = facade.save(req); 
            }
        }

        if (body.getUpdateIds() != null) {
            for (CartDTORequest piece : body.getUpdateIds()) {
                if (piece == null) continue;

                CartDTORequest dto = new CartDTORequest();
                dto.setUser(body.getUserId());
                dto.setBook(piece.getBook()); 
                dto.setQuantity(piece.getQuantity());

                FacadeRequest req = new FacadeRequest();
                req.setPreCommand(updateCardCommand); 
                req.setEntity(dto);

                lastResponse = facade.update(req); 
            }
        }

        if (body.getDeleteIds() != null) {
            for (CartDTORequest piece : body.getDeleteIds()) {
                System.out.println("tch");
                if (piece == null) continue;

                CartDTORequest dto = new CartDTORequest();
                dto.setUser(body.getUserId());
                dto.setBook(piece.getBook()); 

                FacadeRequest req = new FacadeRequest();
                req.setPreCommand(deleteCardCommand); 
                req.setEntity(dto);

                lastResponse = facade.delete(req); 
            }
        }
        
        return ResponseEntity.ok(lastResponse);
    }
}
