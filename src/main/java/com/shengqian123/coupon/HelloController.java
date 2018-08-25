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
    public String greeting(Model model, @RequestParam(value = "id") String id) {
        model.addAttribute("picUrl", "http://img.alicdn.com/tfscom/i2/136542304/TB2Q2koc0LO8KJjSZPcXXaV0FXa_!!136542304.jpg");
        model.addAttribute("secret", "￥u99Xb2oy3Vj￥");


        return "/couponDetail";
    }
}