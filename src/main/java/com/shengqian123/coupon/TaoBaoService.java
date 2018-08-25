package com.shengqian123.coupon;

import com.google.gson.Gson;
import com.taobao.api.*;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import org.springframework.boot.json.GsonJsonParser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TaoBaoService {
    public static final String url = "http://gw.api.taobao.com/router/rest";
    public static final String appKey = "25040990";
    public static final String appSecret = "61e2358b2a7db9fa92b85e7d56619cfc";
    public static final String userId = "1804808162";
//    public static final TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
    public static final TaobaoClient client =  new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23825857", "58a2fff3c1c40fcd0293adac41aedfaa");

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
        Map<String, Object> reponse1 =(Map<String, Object>) reponse.get("tbk_item_get_response");
        Map<String, Object> results = (Map<String, Object>) reponse1.get("results");
        List<Map<String, Object>> items = (List<Map<String, Object>>) results.get("n_tbk_item");
        System.out.println(items.get(1));
        return "";
    }

    public String taoSecret(String text, String url, String logo) throws ApiException {
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        req.setUserId(userId);
        req.setText(text);
        req.setUrl(url);
        req.setLogo(logo);
        req.setExt("{}");
        TbkTpwdCreateResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return rsp.getBody();
    }


    public String getCoupons(String key) throws ApiException {
//        TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
//        TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
//        req.setAdzoneId(17238000002L);
//        req.setPlatform(1L);
//        req.setPageNo(1L);
//        req.setPageSize(5L);
//        req.setQ("男鞋");
//        TbkDgItemCouponGetResponse rsp = client.execute(req);
//        System.out.println(rsp.getBody());

        TbkDgItemCouponGetRequest tbkDgItemCouponGetRequest = new TbkDgItemCouponGetRequest();
        tbkDgItemCouponGetRequest.setAdzoneId(106100645L);
        tbkDgItemCouponGetRequest.setPageSize(5L);
        tbkDgItemCouponGetRequest.setPageNo(1L);
        tbkDgItemCouponGetRequest.setQ("男鞋");
        TbkDgItemCouponGetResponse execute;
        execute = client.execute(tbkDgItemCouponGetRequest);

        Gson gson = new Gson();

        Map<String, Object> reponse = gson.fromJson(execute.getBody(), Map.class);
        Map<String, Object> reponse1 =(Map<String, Object>) reponse.get("tbk_dg_item_coupon_get_response");
        Map<String, Object> results = (Map<String, Object>) reponse1.get("results");
        List<Map<String, Object>> items = (List<Map<String, Object>>) results.get("tbk_coupon");
        System.out.println(items.get(1));
        Map<String, Object> item = items.get(1);
        String text = (String)item.get("title");
        String url = (String)item.get("coupon_click_url");
        String logo = (String)item.get("pict_url");
        return taoSecret(text, url, logo);
    }
}
