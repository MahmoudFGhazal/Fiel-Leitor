package com.mahas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

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

import com.mahas.command.pre.base.sale.BaseSaleCommand;
import com.mahas.command.pre.rules.VerifyCancelSale;
import com.mahas.command.pre.rules.VerifyConfirmPayment;
import com.mahas.command.pre.rules.VerifyCreateSaleBook;
import com.mahas.command.pre.rules.VerifyCreateSaleCard;
import com.mahas.command.pre.rules.VerifyCreateTraderCoupon;
import com.mahas.command.pre.rules.VerifyDecreaseStock;
import com.mahas.command.pre.rules.VerifyDefineSaleStatus;
import com.mahas.command.pre.rules.VerifyDefineTransport;
import com.mahas.command.pre.rules.VerifyNewSale;
import com.mahas.command.pre.rules.VerifyReleaseReservedStock;
import com.mahas.command.pre.rules.VerifyUsedPromotionalCoupon;
import com.mahas.command.pre.rules.VerifyUsedTraderCoupon;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.request.product.BookDTORequest;
import com.mahas.dto.request.sale.PromotionalCouponDTORequest;
import com.mahas.dto.request.sale.SaleBookDTORequest;
import com.mahas.dto.request.sale.SaleCardDTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.sale.SaleBookDTOResponse;
import com.mahas.dto.response.sale.SaleDTOResponse;
import com.mahas.dto.response.sale.TraderCouponDTOResponse;
import com.mahas.facade.IFacade;

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
    private VerifyReleaseReservedStock verifyReleaseReservedStock;

    @Autowired
    private VerifyUsedPromotionalCoupon verifyUsedPromotionalCoupon;

    @Autowired
    private VerifyUsedTraderCoupon verifyUsedTraderCoupon;

    @Autowired
    private VerifyCreateTraderCoupon verifyCreateTraderCoupon;

    @Autowired
    private VerifyDefineTransport verifyDefineTransport;

    @Autowired
    private VerifyDefineSaleStatus verifyDefineSaleStatus;

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

    @PutMapping("/cancel")
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

        FacadeResponse facadeSaleRes = facade.update(saleReq);
        
        DTOResponse entity = facadeSaleRes.getData().getEntity();
        SaleDTOResponse saleRes = (SaleDTOResponse) entity;

        BigDecimal price = java.math.BigDecimal.ZERO;
        if (saleRes.getSaleBooks() != null) {
            for (SaleBookDTOResponse sb : saleRes.getSaleBooks()) {
                if (sb == null) continue;
                BigDecimal unit = sb.getPrice() != null ? sb.getPrice() : BigDecimal.ZERO;
                int qty = java.util.Objects.requireNonNullElse(sb.getQuantity(), 1);
                price = price.add(unit.multiply(java.math.BigDecimal.valueOf(qty)));
            }
        }

        BigDecimal discount = BigDecimal.ZERO;
        if (saleRes.getTraderCoupons() != null) {
            for (TraderCouponDTOResponse tc : saleRes.getTraderCoupons()) {
                if (tc == null) continue;
                BigDecimal v = tc.getValue() != null ? tc.getValue() : BigDecimal.ZERO;
                discount = discount.add(v);
            }
        }
        if (saleRes.getPromotionalCoupon() != null) {
            BigDecimal v = saleRes.getPromotionalCoupon().getValue();
            if (v != null) discount = discount.add(v);
        }

        int cmp = price.compareTo(discount);

        if(cmp > 0) {
            for(SaleCardDTORequest saleCard : sale.getCards()) {
                FacadeRequest saleCardReq = new FacadeRequest();

                saleCard.setSale(saleRes.getId());
                saleCardReq.setEntity(saleCard);
                saleCardReq.setPreCommand(verifyCreateSaleCard);

                facade.update(saleCardReq);
            }
        }else if(cmp < 0) {
            BigDecimal leftover = discount.subtract(price);

            FacadeRequest traderCouponReq = new FacadeRequest();

            TraderCouponDTORequest traderCoupon = new TraderCouponDTORequest();
            traderCoupon.setOriginSale(saleRes.getId());
            traderCoupon.setValue(leftover);
            traderCoupon.setUser(saleRes.getUser().getId());

            traderCouponReq.setEntity(traderCoupon);
            traderCouponReq.setPreCommand(verifyCreateTraderCoupon);

            facadeSaleRes = facade.save(traderCouponReq);
        }

        FacadeRequest promotionalCouponReq = new FacadeRequest();

        PromotionalCouponDTORequest promotionalCoupon = new PromotionalCouponDTORequest();
        promotionalCoupon.setId(sale.getPromotionalCoupon());

        promotionalCouponReq.setEntity(promotionalCoupon);
        promotionalCouponReq.setPreCommand(verifyUsedPromotionalCoupon);

        facade.update(promotionalCouponReq);

        for(Integer traderCouponId : sale.getTraderCoupons()) {
            FacadeRequest traderCouponReq = new FacadeRequest();

            TraderCouponDTORequest traderCoupon = new TraderCouponDTORequest();
            traderCoupon.setId(traderCouponId);

            traderCouponReq.setEntity(traderCoupon);
            traderCouponReq.setPreCommand(verifyUsedTraderCoupon);

            facade.update(traderCouponReq);
        }
        
        return ResponseEntity.ok(facadeSaleRes);
    }

    @PutMapping("/transit")
    public ResponseEntity<FacadeResponse> defineTransport(
        @RequestParam(value = "saleId", required = true) Integer saleId,
        @RequestParam(value = "deliveryDate", required = true) LocalDate deliveryDate,
        @RequestParam(value = "freight", required = true) BigDecimal freight
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        SaleDTORequest sale = new SaleDTORequest();
        sale.setId(saleId);
        sale.setDeliveryDate(deliveryDate);
        sale.setFreight(freight);
        sale.setStatusName(StatusSaleName.IN_TRANSIT);

        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyDefineTransport);

        FacadeResponse facadeSaleRes = facade.update(saleReq);
  
        return ResponseEntity.ok(facadeSaleRes);
    }

    @PutMapping("/delivered")
    public ResponseEntity<FacadeResponse> defineDelivered(
        @RequestParam(value = "saleId", required = true) Integer saleId
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        SaleDTORequest sale = new SaleDTORequest();
        sale.setId(saleId);
        sale.setStatusName(StatusSaleName.DELIVERED);

        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyDefineSaleStatus);

        FacadeResponse facadeSaleRes = facade.update(saleReq);
  
        return ResponseEntity.ok(facadeSaleRes);
    }

    @PutMapping("/trade/request")
    public ResponseEntity<FacadeResponse> requestTrade(
        @RequestParam(value = "saleId", required = true) Integer saleId
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        SaleDTORequest sale = new SaleDTORequest();
        sale.setId(saleId);
        sale.setStatusName(StatusSaleName.EXCHANGE_REQUESTED);

        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyDefineSaleStatus);

        FacadeResponse facadeSaleRes = facade.update(saleReq);
  
        return ResponseEntity.ok(facadeSaleRes);
    }

    @PutMapping("/trade/status")
    public ResponseEntity<FacadeResponse> defineStatusTrade(
        @RequestParam(value = "saleId", required = true) Integer saleId,
        @RequestParam(value = "confirm", required = true) Boolean confirm
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        SaleDTORequest sale = new SaleDTORequest();
        sale.setId(saleId);

        if(confirm) {
            sale.setStatusName(StatusSaleName.EXCHANGE_AUTHORIZED);
        }else {
            sale.setStatusName(StatusSaleName.DECLINED);
        }

        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyDefineSaleStatus);

        FacadeResponse facadeSaleRes = facade.update(saleReq);

        return ResponseEntity.ok(facadeSaleRes);
    }

    @PutMapping("/trade/delivered")
    public ResponseEntity<FacadeResponse> defineTradeDelivered(
        @RequestParam(value = "saleId", required = true) Integer saleId
    ) {
        FacadeRequest saleReq = new FacadeRequest();
        
        SaleDTORequest sale = new SaleDTORequest();
        sale.setId(saleId);
        sale.setStatusName(StatusSaleName.EXCHANGED);


        saleReq.setEntity(sale);
        saleReq.setPreCommand(verifyDefineSaleStatus);

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

}


