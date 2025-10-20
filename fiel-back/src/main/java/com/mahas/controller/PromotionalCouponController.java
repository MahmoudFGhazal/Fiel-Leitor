package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.pre.base.sale.BasePromotionalCouponCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.sale.PromotionalCouponDTORequest;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("promotionalCoupon")
public class PromotionalCouponController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BasePromotionalCouponCommand basePromotionalCouponCommand;

    @GetMapping("/check")
    public ResponseEntity<FacadeResponse> checkTraderCoupon(
        @RequestParam(value = "code", required = true) String code
    ) {
        FacadeRequest request = new FacadeRequest();
        
       PromotionalCouponDTORequest promotionalCouponReq = new PromotionalCouponDTORequest();
        promotionalCouponReq.setCode(code);

        request.setEntity(promotionalCouponReq);
        request.setPreCommand(basePromotionalCouponCommand);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

}
