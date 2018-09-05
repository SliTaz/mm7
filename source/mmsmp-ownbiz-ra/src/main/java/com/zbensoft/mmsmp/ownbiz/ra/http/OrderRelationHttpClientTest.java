package com.zbensoft.mmsmp.ownbiz.ra.http;

import com.zbensoft.mmsmp.ownbiz.ra.own.util.ValidateMessageUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class OrderRelationHttpClientTest {
	public static void main(String[] args) {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpPost post = new HttpPost("http://localhost:8080/MmsOwnbiz/orderRelationReceiveServlet");

		post.setHeader("mmsp-account-id", "123");
		post.setHeader("mmsp-user-number", "13012341234");
		post.setHeader("mmsp-product-id", "3100201701");
		post.setHeader("mmsp-fee-type", "0");
		post.setHeader("mmsp-message-id", "4389744ed7b646b9b235f0002ab793d4");
		post.setHeader("mmsp-validate-code", "ctmmiw");

		StringBuffer sb = new StringBuffer();
		sb.append("order").append("3100201701").append("0").append("123").append("13012341234")
				.append("4389744ed7b646b9b235f0002ab793d4").append("ctmmiw");
		String content = ValidateMessageUtil.getMD5String(sb.toString());
		post.setHeader("Content", content);
		try {
			HttpResponse response = httpClient.execute(post);
			System.out.println("return:  " + response.getStatusLine());
		} catch (Exception localException) {
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
}
