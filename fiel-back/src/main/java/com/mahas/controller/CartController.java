package com.mahas.controller;

import com.mahas.command.post.adapters.AddCartAdapter;
import com.mahas.command.post.adapters.DeleteCartAdapter;
import com.mahas.command.pre.base.product.BaseCartCommand;
import com.mahas.command.pre.rules.VerifyCreateCart;
import com.mahas.command.pre.rules.VerifyDeleteCart;
import com.mahas.command.pre.rules.VerifyUpdateCart;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.product.CartDTORequest;
import com.mahas.dto.request.product.cart.AddCartRequest;
import com.mahas.dto.request.product.cart.UpdateCartRequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.product.CartDTOResponse;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private DeleteCartAdapter deleteCartAdapter;

    @Autowired
    private AddCartAdapter addCartAdapter;

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

    @PostMapping("/add")
    public ResponseEntity<FacadeResponse> addCart(@RequestBody AddCartRequest body) {
        FacadeResponse lastResponse = new FacadeResponse();

        if (body.getAddIds() != null) {
            for (CartDTORequest piece : body.getAddIds()) {
                if (piece == null) continue;

                CartDTORequest dto = new CartDTORequest();
                dto.setUser(body.getUserId());
                dto.setBook(piece.getBook()); 
                dto.setQuantity(piece.getQuantity());

                FacadeRequest req = new FacadeRequest();
                req.setPreCommand(createCardCommand);
                req.setPostCommand(addCartAdapter); 
                req.setEntity(dto);

                lastResponse = facade.save(req); 
            }
        }
        
        return ResponseEntity.ok(lastResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<FacadeResponse> deleteCart(
        @RequestParam(value = "userId", required = true) Integer userId
    ) {
        FacadeRequest queryRequest = new FacadeRequest();

        CartDTORequest cartRequest = new CartDTORequest();
        cartRequest.setUser(userId);
        queryRequest.setEntity(cartRequest);
        queryRequest.setPreCommand(baseCartCommand);

        FacadeResponse queryResponse = facade.query(queryRequest);

        if(queryResponse.getData().getEntities() != null) {
            for (DTOResponse entity : queryResponse.getData().getEntities()) {
                if (entity == null) continue;

                CartDTOResponse dtoRes = (CartDTOResponse) entity;

                CartDTORequest dtoReq = new CartDTORequest();
                dtoReq.setBook(dtoRes.getBook().getId());
                dtoReq.setUser(dtoRes.getUser().getId());

                FacadeRequest req = new FacadeRequest();
                req.setPreCommand(deleteCardCommand); 
                req.setPostCommand(deleteCartAdapter);
                req.setEntity(dtoReq);

                queryResponse = facade.delete(req); 

                
            }
        }

        return ResponseEntity.ok(queryResponse);
    }
}
