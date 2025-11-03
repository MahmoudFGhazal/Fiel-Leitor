package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.pre.base.sale.BaseTraderCouponCommand;
import com.mahas.command.pre.rules.VerifyGetTraderCouponUser;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/traderCoupon")
public class TraderCouponController {
    @Autowired
    private IFacade facade;

    @Autowired
    private VerifyGetTraderCouponUser verifyGetTraderCouponUser;

    @Autowired
    private BaseTraderCouponCommand baseTraderCouponCommand;

    @GetMapping("/check")
    public ResponseEntity<FacadeResponse> checkTraderCoupon(
        @RequestParam(value = "code", required = true) String code
    ) {
        FacadeRequest request = new FacadeRequest();
        
        TraderCouponDTORequest traderCouponReq = new TraderCouponDTORequest();
        traderCouponReq.setCode(code);
        traderCouponReq.setUsed(false);

        request.setEntity(traderCouponReq);
        request.setPreCommand(baseTraderCouponCommand);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<FacadeResponse> getTraderCouponByUser(
        @RequestParam(value = "userId", required = true) Integer userId
    ) {
        FacadeRequest request = new FacadeRequest();
        
        TraderCouponDTORequest traderCouponReq = new TraderCouponDTORequest();
        traderCouponReq.setUser(userId);

        request.setEntity(traderCouponReq);
        request.setPreCommand(verifyGetTraderCouponUser);

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

}
