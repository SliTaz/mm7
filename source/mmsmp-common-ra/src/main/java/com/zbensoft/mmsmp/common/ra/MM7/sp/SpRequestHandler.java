package com.zbensoft.mmsmp.common.ra.MM7.sp;

import com.zbensoft.mmsmp.common.ra.common.message.HttpRequest;
import org.apache.log4j.Logger;

public class SpRequestHandler {
	private static final Logger logger = Logger.getLogger(SpRequestHandler.class);
	private SpDao spdao;

	public SubmitReq getSubmit(HttpRequest request) {
		String content = request.getContent();

		SubmitReq submit = new SubmitReq();
		submit.parser(content);
		return submit;
	}

	public void saveSubmit(SubmitReq submit) {
	}

	public void updateRequest(HttpRequest request) {
		String xml = request.getResponse();

		SubmitResp resp = new SubmitResp();
		resp.parser(xml);
	}

	public String getUrl(String vaspid) {
		return "http://127.0.0.1/SPreseriver";
	}

	public String getProduct_idByService_code(String serviceCode) {
		return "222222222222";
	}
}
