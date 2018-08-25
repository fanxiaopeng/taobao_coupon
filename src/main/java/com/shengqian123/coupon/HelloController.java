package com.shengqian123.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fanxiaopeng
 */
@Controller
public class HelloController {


    @RequestMapping("/greeting")
    public String greeting(Model model, @RequestParam(value = "keyword") String keyword) {

        TaoBaoService taoBaoService = new TaoBaoService();
        CouponDO couponDO = taoBaoService.getCoupons(keyword);

        model.addAttribute("picUrl", couponDO.getLogo());
        model.addAttribute("secret", couponDO.getSecret());
        model.addAttribute("text", couponDO.getText());
        return "/couponDetail";
    }
}