package com.mahas.controller;

import com.mahas.command.pre.base.sale.BaseSaleCommand;
import com.mahas.command.pre.rules.VerifyCancelSale;
import com.mahas.command.pre.rules.VerifyConfirmPayment;
import com.mahas.command.pre.rules.VerifyCreateSaleBook;
import com.mahas.command.pre.rules.VerifyCreateSaleCard;
import com.mahas.command.pre.rules.VerifyDecreaseStock;
import com.mahas.command.pre.rules.VerifyNewSale;
import com.mahas.command.pre.rules.verifyReleaseReservedStock;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.dto.request.sale.SaleBookDTORequest;
import com.mahas.dto.request.sale.SaleCardDTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.sale.SaleBookDTOResponse;
import com.mahas.dto.response.sale.SaleDTOResponse;
import com.mahas.facade.IFacade;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BaseSaleCommand baseSaleCommand;

    @Autowired
    private VerifyCreateSaleBook verifyCreateSaleBook;

    @Autowired
    private VerifyCreateSaleCard verifyCreateSaleCard;

    @Autowired
    private VerifyNewSale verifyNewSale;

    @Autowired
    private VerifyDecreaseStock verifyDecreaseStock;

    @Autowired
    private VerifyConfirmPayment verifyConfirmPayment;

    @Autowired
    private VerifyCancelSale verifyCancelSale;

    @Autowired
    private verifyReleaseReservedStock verifyReleaseReservedStock;

    @GetMapping("/user")
    public ResponseEntity<FacadeResponse> getSaleByUser(
        @RequestParam(value = "userId", required = true) Integer userId
    ) {
        FacadeRequest request = new FacadeRequest();
        
        SaleDTORequest sale = new SaleDTORequest();
        sale.setUser(userId);

        request.setEntity(sale);
        request.setPreCommand(baseSaleCommand);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("cancel")
    public ResponseEntity<FacadeResponse> cancelSale(
        @RequestBody SaleDTORequest sale
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyCancelSale);

        FacadeResponse facadeSaleRes = facade.update(saleReq);
        
        SaleDTOResponse saleRes = (SaleDTOResponse) facadeSaleRes.getData().getEntity();

        for(SaleBookDTOResponse saleBook : saleRes.getSaleBooks()) {
            FacadeRequest bookReq = new FacadeRequest();

            BookDTORequest book = new BookDTORequest();

            book.setId(saleBook.getBook().getId());
            book.setStock(saleBook.getQuantity());

            bookReq.setEntity(book);
            bookReq.setPreCommand(verifyReleaseReservedStock);

            facade.update(bookReq);
        }
        
        return ResponseEntity.ok(facadeSaleRes);
    }

    @PostMapping
    public ResponseEntity<FacadeResponse> newSale(
        @RequestBody SaleDTORequest sale
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyNewSale);

        FacadeResponse facadeSaleRes = facade.save(saleReq);
        
        DTOResponse entity = facadeSaleRes.getData().getEntity();
        SaleDTOResponse saleRes = (SaleDTOResponse) entity;

        for(SaleBookDTORequest saleBook : sale.getBooks()) {
            FacadeRequest saleBookReq = new FacadeRequest();

            saleBook.setSale(saleRes.getId());
            saleBookReq.setEntity(saleBook);
            saleBookReq.setPreCommand(verifyCreateSaleBook);

            facade.save(saleBookReq);

            FacadeRequest bookReq = new FacadeRequest();

            BookDTORequest book = new BookDTORequest();
            book.setId(saleBook.getBook());
            book.setStock(saleBook.getQuantity());

            bookReq.setEntity(book);
            bookReq.setPreCommand(verifyDecreaseStock);

            facade.update(bookReq);
        }
        
        return ResponseEntity.ok(facadeSaleRes);
    }

    @PutMapping("/payment")
    public ResponseEntity<FacadeResponse> confirmPayment(
        @RequestBody SaleDTORequest sale
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyConfirmPayment);

        FacadeResponse facadeSaleRes = facade.save(saleReq);
        
        DTOResponse entity = facadeSaleRes.getData().getEntity();
        SaleDTOResponse saleRes = (SaleDTOResponse) entity;

        for(SaleCardDTORequest saleCard : sale.getCards()) {
            FacadeRequest saleCardReq = new FacadeRequest();

            saleCard.setSale(saleRes.getId());
            saleCardReq.setEntity(saleCard);
            saleCardReq.setPreCommand(verifyCreateSaleCard);

            facade.update(saleCardReq);
        }
        
        return ResponseEntity.ok(facadeSaleRes);
    }

}
