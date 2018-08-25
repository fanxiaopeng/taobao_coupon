package com.shengqian123.coupon;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fanxiaopeng
 */
@Data
public class CouponDO {

    /**
     * 文案
     */
    private String text;

    /**
     * 领券URL
     */
    private String url;

    /**
     * 图标
     */
    private String logo;


    /**
     * 淘口令
     */
    private String secret;

}
