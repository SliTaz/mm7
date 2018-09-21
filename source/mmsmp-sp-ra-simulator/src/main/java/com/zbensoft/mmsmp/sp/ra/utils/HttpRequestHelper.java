package com.zbensoft.mmsmp.sp.ra.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestHelper {

	private String API_URL = "http://localhost:8080";// 调用api的url

	private static HttpRequestHelper hrh = new HttpRequestHelper();

	public static String getSpProductIdsForSPsimulator(String vasId,String command) {
		String spProductIds = "";
		RequestBody body = new FormBody.Builder().build();
		Request request = new Request.Builder().url(hrh.API_URL + "/corbiz/getSpProductIdsForSPsimulator?vasId=" + vasId+"&command="+command)
				.get().build();
		OkHttpClient okHttp = new OkHttpClient();
		Call call = okHttp.newCall(request);
		Response response = null;
		try {
			response = call.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(responseData);
		String statusCode = jsonObject.getString("statusCode");
		
		if ("OK".equals(statusCode)) {
			JSONArray bodyArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < bodyArray.size(); i++) {
				if (i == 0) {
					spProductIds = bodyArray.getString(i);
				} else {
					spProductIds = spProductIds + "," + bodyArray.getString(i);
				}
			}

		}
		return spProductIds;
	}

}
