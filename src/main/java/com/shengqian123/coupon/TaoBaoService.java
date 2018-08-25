package com.shengqian123.coupon;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author fanxiaopeng
 */
@Slf4j
public class TaoBaoService {
    public static final String url = "http://gw.api.taobao.com/router/rest";
    public static final String appKey = "25040990";
    public static final String appSecret = "61e2358b2a7db9fa92b85e7d56619cfc";
    public static final String userId = "1804808162";
    public static final Long adzoneId = 106100645L;
    //    public static final TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);

    public static final TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23825857", "58a2fff3c1c40fcd0293adac41aedfaa");

    public static final String pid = "mm_133297853_92100254_17238000002";

    public String searchItem(String key) throws ApiException {
        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ(key);
        req.setSort("tk_rate_des");
        req.setIsTmall(false);
        req.setIsOverseas(false);
        req.setPageSize(5L);
        Gson gson = new Gson();

        TbkItemGetResponse rsp = client.execute(req);
        Map<String, Object> reponse = gson.fromJson(rsp.getBody(), Map.class);
        Map<String, Object> reponse1 = (Map<String, Object>) reponse.get("tbk_item_get_response");
        Map<String, Object> results = (Map<String, Object>) reponse1.get("results");
        List<Map<String, Object>> items = (List<Map<String, Object>>) results.get("n_tbk_item");
        System.out.println(items.get(1));
        return "";
    }

    public String taoSecret(String text, String url, String logo) {
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        req.setUserId(userId);
        req.setText(text);
        req.setUrl(url);
        req.setLogo(logo);
        req.setExt("{}");
        TbkTpwdCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        Map<String, Object> response = gson.fromJson(rsp.getBody(), Map.class);
        Map<String, Object> response1 = (Map<String, Object>) response.get("tbk_tpwd_create_response");
        Map<String, Object> results = (Map<String, Object>) response1.get("data");
        String secret = (String) results.get("model");
        return secret;
    }


    public CouponDO getCoupons(String key) {


        TbkDgItemCouponGetRequest tbkDgItemCouponGetRequest = new TbkDgItemCouponGetRequest();
        tbkDgItemCouponGetRequest.setAdzoneId(adzoneId);
        tbkDgItemCouponGetRequest.setPageSize(5L);
        tbkDgItemCouponGetRequest.setPageNo(1L);
        tbkDgItemCouponGetRequest.setQ(key);
        TbkDgItemCouponGetResponse execute;
        try {
            execute = client.execute(tbkDgItemCouponGetRequest);
        } catch (ApiException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Map<String, Object> response = gson.fromJson(execute.getBody(), Map.class);
        Map<String, Object> response1 = (Map<String, Object>) response.get("tbk_dg_item_coupon_get_response");
        Map<String, Object> results = (Map<String, Object>) response1.get("results");
        List<Map<String, Object>> items = (List<Map<String, Object>>) results.get("tbk_coupon");
        System.out.println(items.get(1));
        Map<String, Object> item = items.get(1);

        CouponDO couponDO = new CouponDO();
        String text = (String) item.get("title");
        String url = (String) item.get("coupon_click_url");
        String logo = (String) item.get("pict_url");
        String secret = taoSecret(text, url, logo);
        couponDO.setLogo(logo);
        couponDO.setSecret(secret);
        couponDO.setText(text);
        return couponDO;
    }
}
