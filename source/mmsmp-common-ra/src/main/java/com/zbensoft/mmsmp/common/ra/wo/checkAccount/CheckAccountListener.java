package com.zbensoft.mmsmp.common.ra.wo.checkAccount;

import com.zbensoft.mmsmp.common.ra.wo.util.PropertiesHelper;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

public class CheckAccountListener implements ServletContextListener {
	private Timer scanRingExpiryDateTimer = new Timer();
	private String STARTTIME = PropertiesHelper.getString("checkAccount_startTime", null);
	private String INTERVAL = "24";

	private static final Logger logger = Logger.getLogger(CheckAccountListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		this.scanRingExpiryDateTimer.cancel();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			final WebApplicationContext wac = (WebApplicationContext) arg0.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			final String theDay = format.format(new Date());
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				this.scanRingExpiryDateTimer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						System.out.println(theDay + " " + CheckAccountListener.this.STARTTIME);

						CheckAccountClient checkAccountClient = (CheckAccountClient) wac.getBean("checkAccountClient");
						checkAccountClient.check(PropertiesHelper.getString("wo.check.account.servlet", null));
					}
				}, format.parse(theDay + " " + this.STARTTIME), Integer.parseInt(this.INTERVAL) * 1000L * 3600L);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
