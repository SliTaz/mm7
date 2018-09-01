package com.zbensoft.mmsmp.sp.ra.spagent.sp.mtmms;

import com.zbensoft.mmsmp.common.ra.mms.xmlutil.service.impl.VaspXmlServiceImpl;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.HttpRequest;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ReceiveValidateFilter implements Filter {
	static final Logger logger = Logger.getLogger(ReceiveValidateFilter.class);
	static String spIpConfigFilePath;

	public void init(FilterConfig config) throws ServletException {
		spIpConfigFilePath = config.getInitParameter("spIpConfigFilePath");
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		String remoteIP = getRemoteIP(request);
		VaspXmlServiceImpl vaspXmlService = new VaspXmlServiceImpl();
		vaspXmlService.setSpIpConfigFilePath(spIpConfigFilePath);

		SubmitReq submit = new SubmitReq();
		submit.parser(new HttpRequest(request).getContent());
		try {
			String[] sp = vaspXmlService.getSp(submit.getVASPID());
			if ((sp != null) && ((!sp[0].equals(remoteIP)) || (!sp[1].equals(submit.getSenderAddress())))) {
				logger.error("validate fail-------spId:" + submit.getVASPID() + ",sendAddress:"
						+ submit.getSenderAddress() + ",configSendAddress:" + sp[1] + ",realIp:"
						+ request.getRemoteAddr() + ",configIp:" + sp[0]);
				return;
			}
		} catch (Exception e1) {
			e1.printStackTrace();

			logger.info(submit.getVASPID() + " IP Validated.");
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	static String getRemoteIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}

		return request.getHeader("x-forwarded-for");
	}
}
