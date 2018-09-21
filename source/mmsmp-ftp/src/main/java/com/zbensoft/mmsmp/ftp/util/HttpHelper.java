package com.zbensoft.mmsmp.ftp.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zbensoft.mmsmp.ftp.sysc.productInfoFtp.ProductInfo;
import com.zbensoft.mmsmp.ftp.sysc.serviceInfoFtp.ServiceInfo;
import com.zbensoft.mmsmp.ftp.sysc.spftp.req.SpInfoReq;
import okhttp3.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHelper {
    private static final String SUCCESS_CODE = "OK";

    static String apiUrl = "";

    private static String API_KEY = "&api_key=0029e8bbc02fce75eb7f60d957765a9e";

    static {
        apiUrl = PropertiesUtil.get("application", "mmsmp.api.url");
    }

    static MediaType j = MediaType.parse("application/json; charset=utf-8");

    static String GET_SPCOUNT_URL = apiUrl + "/spInfo/getSpInfoCountById?spId={spId}" + API_KEY;

    static String SYSC_UPDATE_SP = apiUrl + "/spInfo/sycUpdateSpInfo??z=z&updateType={updateType}" + API_KEY;

    static String SYSC_DELETE_SP = apiUrl + "/spInfo/sycDeleteSpInfo?spId={spId}" + API_KEY;

    static String SYSC_GET_ALL_SP_IDS = apiUrl + "/spInfo/getAllSpIds?z=z" + API_KEY;

    static String SYSC_SAVE_OR_UPDATE_PRODUCT_INFO = apiUrl + "/productInfo/sysSaveOrUpdateProduct";

    static String SYSC_DELETE_PRODUCT_INFO = apiUrl + "/productInfo/deleteProductInfo?z=z" + API_KEY;

    static String SAVE_OR_UPDATE_ACCESS_URL = apiUrl + "/spAccess/sysSaveOrUpdateAccess?api_key={1}";

    static String DELETE_ACCESS_URL = apiUrl + "/spAccess/sysDeleteAccess?z=z" + API_KEY;

    static String QUERY_FOR_SERVICE_ID_LIST = apiUrl + "/spAccess/queryAccessIdList?z=z" + API_KEY;


    private static Logger log = LogManager.getLogger(HttpHelper.class);


    public static int getSpCount(String spid) {
        try {
            Request request = new Request.Builder()
                    .url(GET_SPCOUNT_URL.replace("{spId}", spid))
                    .get()
                    .build();
            OkHttpClient okHttp = new OkHttpClient();
            Call call = okHttp.newCall(request);
            Response response = call.execute();
            String responseData = response.body().string();
            return Integer.parseInt(responseData);
        } catch (Exception e) {
            log.error("getSpCount error", e);
            return 0;
        }

    }

    public static void updateSp(SpInfoReq spInfoReq, int updateType) throws IOException {
        RequestBody body = RequestBody.create(j, JSON.toJSONString(spInfoReq));
        Request request = new Request.Builder()
                .url(SYSC_UPDATE_SP.replace("{updateType}", String.valueOf(updateType))).post(body)
                .build();
        OkHttpClient okHttp = new OkHttpClient();
        Call call = okHttp.newCall(request);
        Response response = call.execute();
        JSONObject responseObj = JSON.parseObject(response.body().string());
        if (!SUCCESS_CODE.equals(responseObj.getString("statusCode")))
            throw new RuntimeException("Update sp info error!");
    }

    public static void deleteSpInfo(String spid) {
        try {
            Request request = new Request.Builder()
                    .url(SYSC_DELETE_SP.replace("{spId}", spid)).get()
                    .build();
            OkHttpClient okHttp = new OkHttpClient();
            Call call = okHttp.newCall(request);
            Response response = call.execute();
            JSONObject responseObj = JSON.parseObject(response.body().string());
            if (!SUCCESS_CODE.equals(responseObj.getString("statusCode")))
                throw new RuntimeException("delete sp info error!");
        } catch (Exception e) {
            log.error("getSpCount error", e);
        }
    }

    public static List<String> getALLSpIds() {
        List<String> spIds = null;
        try {
            Request request = new Request.Builder()
                    .url(SYSC_GET_ALL_SP_IDS)
                    .get()
                    .build();
            OkHttpClient okHttp = new OkHttpClient();
            Call call = okHttp.newCall(request);
            Response response = call.execute();
            JSONObject responseObj = JSON.parseObject(response.body().string());
            if (!SUCCESS_CODE.equals(responseObj.getString("statusCode")))
                throw new RuntimeException("get sp id list error!");

            spIds = JSON.parseObject(responseObj.get("body").toString(), List.class);
        } catch (Exception e) {
            log.error("getSpCount error", e);
        }
        return spIds;
    }

    public static void saveOrUpdateProductInfo(ProductInfo productInfo, int productType, double charge) {
        RestTemplate restTemplate = new RestTemplate();
   //     try {

//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
//            HttpEntity<ProductInfo> entity = new HttpEntity<>(productInfo, headers);
//
//            Map<String, Object> param = new HashMap<>();
//            param.put("api_key", "0029e8bbc02fce75eb7f60d957765a9e");
//            param.put("charge", charge);
//            ResponseEntity<String> responseEntity = restTemplate.postForEntity(SYSC_SAVE_OR_UPDATE_PRODUCT_INFO, entity, String.class, param);
//            if (responseEntity.getStatusCode().equals(HttpStatus.OK))
//                throw new RuntimeException("save or update product info error");
//        } catch (Exception e) {
//            log.error("save or update product info error,service id" + productInfo.serviceID, e);
//        }
            Map<String, Object> param = new HashMap<>();
            param.put("api_key", "0029e8bbc02fce75eb7f60d957765a9e");
            param.put("charge", charge);
            restTemplate.postForEntity(SYSC_SAVE_OR_UPDATE_PRODUCT_INFO,productInfo,String.class);
    }

    public static void deleteProductInfo(ProductInfo productInfo, int productType, double charge) {
        try {
            Request request = new Request.Builder()
                    .url(SYSC_DELETE_PRODUCT_INFO)
                    .get()
                    .build();
            OkHttpClient okHttp = new OkHttpClient();
            Call call = okHttp.newCall(request);
            Response response = call.execute();
            JSONObject responseObj = JSON.parseObject(response.body().string());
            if (!SUCCESS_CODE.equals(responseObj.getString("statusCode")))
                throw new RuntimeException("delete product info error!");
        } catch (Exception e) {
            log.error("delete product info error", e);
        }
    }

    public static void saveOrUpdateAccess(ServiceInfo serviceInfo) {
        try {
            if ((serviceInfo.updateType == 1) && (serviceInfo.serviceType == null && !serviceInfo.serviceType.equals("31"))) {
                log.error("serviceType == null or service type not equal 31" + serviceInfo.serviceID);
                return;
            }

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(SAVE_OR_UPDATE_ACCESS_URL, serviceInfo, String.class, "0029e8bbc02fce75eb7f60d957765a9e");
            if (responseEntity.getStatusCode().equals(HttpStatus.OK))
                throw new RuntimeException("save or update service info error");
        } catch (Exception e) {
            log.error("save or update service info error,service id" + serviceInfo.serviceID, e);
        }
    }

    public static void deleteAccess(ServiceInfo serviceInfo) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(DELETE_ACCESS_URL, serviceInfo, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK))
                throw new RuntimeException("delete service info error");
        } catch (Exception e) {
            log.error("delete service info error,service id" + serviceInfo.serviceID, e);
        }
    }

    public static List queryListBySql() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity entity = restTemplate.getForEntity(QUERY_FOR_SERVICE_ID_LIST, List.class);
        return (List) entity.getBody();
    }
}
