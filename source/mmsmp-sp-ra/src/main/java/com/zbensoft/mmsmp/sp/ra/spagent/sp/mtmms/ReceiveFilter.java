package com.zbensoft.mmsmp.sp.ra.spagent.sp.mtmms;

import com.zbensoft.mmsmp.sp.ra.spagent.utils.HttpRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ReceiveFilter implements Filter {
	static final Logger logger = Logger.getLogger(ReceiveFilter.class);

	static Map<String, FilteOption> interceptMap = new ConcurrentHashMap();
	static ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
	static SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss[SSS]");
	static String connectionOutCode;
	static String rateOutCode;
	static int flushInterval;
	static String dbDriver;
	static String dbUrl;
	static String dbUsername;
	static String dbPassword;
	static String dbSql;

	public void init(FilterConfig config) throws ServletException {
		connectionOutCode = config.getInitParameter("connectionOutCode") == null ? "5001"
				: config.getInitParameter("connectionOutCode");
		rateOutCode = config.getInitParameter("rateOutCode") == null ? "5002" : config.getInitParameter("rateOutCode");
		dbDriver = config.getInitParameter("dbDriver");
		dbUrl = config.getInitParameter("dbUrl");
		dbUsername = config.getInitParameter("dbUsername");
		dbPassword = config.getInitParameter("dbPassword");
		dbSql = config.getInitParameter("dbSql");
		try {
			flushInterval = Integer.parseInt(config.getInitParameter("flushInterval"));
			flushInterval = flushInterval <= 0 ? 10 : flushInterval;
		} catch (Exception ex) {
			flushInterval = 30;
		}

		execService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				long execTime = System.currentTimeMillis();

				ReceiveFilter.DBUtil dbUtil = new ReceiveFilter.DBUtil();
				List<FilteOption> filteOptions = ReceiveFilter.DBUtil.getFilteOptions();

				if (filteOptions.size() == 0) {
					ReceiveFilter.logger.warn("Mmsmt Receive Intercepter Setting Flush Error[TotalRows:"
							+ filteOptions.size() + ",ExecTime:" + (System.currentTimeMillis() - execTime) + "/ms]");
					return;
				}

				synchronized (ReceiveFilter.interceptMap) {
					ReceiveFilter.interceptMap.clear();

					for (ReceiveFilter.FilteOption option : filteOptions) {
						ReceiveFilter.logger.info("Mmsmt Receive Intercepter Setting Option[Vaspid:"
								+ option.getVaspid() + ",MaxConnection:" + option.getMaxConn() + "/SS,MaxRate:"
								+ option.getMaxCount() + "/s]");

						ReceiveFilter.interceptMap.put(option.getVaspid(), option);
					}

					ReceiveFilter.logger.info("Mmsmt Receive Intercepter Setting Flush Success[TotalRows:"
							+ filteOptions.size() + ",ExecTime:" + (System.currentTimeMillis() - execTime) + "/ms]");
				}
			}
		}, 0L, flushInterval * 60, TimeUnit.SECONDS);

		logger.info("Mmsmt Receive Intercepter Stratup!");
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = new ReceiveWrapper((HttpServletRequest) servletRequest);
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String remoteIP = getRemoteIP(request);
		SubmitReq mmsmt = getMmsMessage(request);

		if (interceptMap.containsKey(mmsmt.getVASPID())) {
			FilteOption intercepter = (FilteOption) interceptMap.get(mmsmt.getVASPID());
			Calendar calendar = Calendar.getInstance();

			int curSec = calendar.get(13);

			int curMsec = calendar.get(14);

			if (curSec == intercepter.getCurSec().get()) {
				curMsec -= curMsec % 100;

				if (curMsec == intercepter.getCurMsec().get()) {
					int curConn = intercepter.getCurConn().incrementAndGet();
					int maxConn = intercepter.getMaxConn().get();

					if (curConn > maxConn) {
						logger.warn("Mmsmt Receive Intercepter Connection Limitted[Vaspid:" + mmsmt.getVASPID()
								+ ",curMsec:" + curMsec + ",CurConn:" + curConn + ",MaxConn:" + maxConn + ",RemoteIp:"
								+ remoteIP + ",ReceiveTime:" + dateFormater.format(calendar.getTime()) + "]");

						String messageId = UUID.randomUUID().toString().replace("-", "");
						String transactionId = mmsmt.getTransactionID();
						String mmsmtReport = SubmitResp.getSubmitResp(transactionId, messageId, connectionOutCode,
								"First:Mmsmp Receive Connection Limitted");

						PrintWriter out = response.getWriter();
						out.write(mmsmtReport);
					}

				} else {
					intercepter.getCurMsec().set(curMsec);
					intercepter.getCurConn().set(1);
				}

				int curCount = intercepter.getCurCount().incrementAndGet();
				int maxCount = intercepter.getMaxCount().get();

				if (curCount > maxCount) {
					logger.warn("Mmsmt Receive Intercepter Rate Limitted[Vaspid:" + mmsmt.getVASPID() + ",curSec:"
							+ curSec + ",CurCount:" + curCount + ",MaxCount:" + maxCount + ",RemoteIp:" + remoteIP
							+ ",ReceiveTime:" + dateFormater.format(calendar.getTime()) + "]");

					String messageId = UUID.randomUUID().toString().replace("-", "");
					String transactionId = mmsmt.getTransactionID();
					String mmsmtReport = SubmitResp.getSubmitResp(transactionId, messageId, rateOutCode,
							"First:Mmsmp Receive Rate Limitted");

					PrintWriter out = response.getWriter();
					out.write(mmsmtReport);
				}

			} else {
				logger.info("MMSmt Received " + intercepter.getCurCount().get() + " request within {"
						+ intercepter.getCurSec().get() + " - " + curSec + "} from SP:" + mmsmt.getVASPID());
				intercepter.getCurSec().set(curSec);
				intercepter.getCurCount().set(1);
			}

			chain.doFilter(request, servletResponse);
		} else {
			logger.warn("Mmsmt Receive Intercepter Access Denied[Vaspid:" + mmsmt.getVASPID() + ",RemoteIp:" + remoteIP
					+ ",ReceiveTime:" + dateFormater.format(new Date()) + "]");

			String messageId = UUID.randomUUID().toString().replace("-", "");
			String transactionId = mmsmt.getTransactionID();
			String mmsmtReport = SubmitResp.getSubmitResp(transactionId, messageId, "2004",
					"First:Mmsmp Multimedia Message Service Access Denied");

			PrintWriter out = response.getWriter();
			out.write(mmsmtReport);
		}
	}

	static SubmitReq getMmsMessage(HttpServletRequest request) {
		SubmitReq mmsmt = new SubmitReq();
		mmsmt.parser(new HttpRequest(request).getContent());

		return mmsmt;
	}

	static String getRemoteIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}

		return request.getHeader("x-forwarded-for");
	}

	static class DBUtil {
		public static synchronized Connection getConnection() throws ClassNotFoundException, SQLException {
			Class.forName(ReceiveFilter.dbDriver);
			return DriverManager.getConnection(ReceiveFilter.dbUrl, ReceiveFilter.dbUsername, ReceiveFilter.dbPassword);
		}

		public static synchronized void close(Connection conn, Statement stmt, ResultSet rset) {
			try {
				if (rset != null)
					rset.close();
			} catch (Exception localException) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception localException1) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception localException2) {
			}
		}

		public static List<ReceiveFilter.FilteOption> getFilteOptions() {
			List options = new ArrayList();

			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			try {
				conn = getConnection();
				stmt = conn.createStatement();
				rset = stmt.executeQuery(ReceiveFilter.dbSql);

				while (rset.next()) {
					String spKey = rset.getString(1);
					int maxConn = rset.getInt(2) < 1 ? 1 : rset.getInt(2);
					int maxCount = rset.getInt(3) < 1 ? 1 : rset.getInt(3);

					if ((spKey != null) && (spKey.trim().length() > 0)) {
						options.add(new ReceiveFilter.FilteOption(spKey.trim(), maxConn, maxCount));
					} else {
						ReceiveFilter.logger.info("Mmsmt Receive Intercepter Load Option Error[Row:" + rset.getRow()
								+ ",Vaspid:" + spKey + ",MaxConnection:" + maxConn + ",MaxRate:" + maxCount + "/s]");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(conn, stmt, rset);
			}

			return options;
		}
	}

	static class FilteOption {
		private String vaspid;
		private AtomicInteger maxCount;
		private AtomicInteger curCount;
		private AtomicInteger curSec;
		private AtomicInteger curMsec;
		private AtomicInteger curConn;
		private AtomicInteger maxConn;

		public FilteOption(String vaspid, int maxConn, int maxCount) {
			Calendar calendar = Calendar.getInstance();

			this.vaspid = vaspid;

			this.curSec = new AtomicInteger(calendar.get(13));
			this.curCount = new AtomicInteger(0);
			this.maxCount = new AtomicInteger(maxCount);

			this.curMsec = new AtomicInteger(calendar.get(14));
			this.curConn = new AtomicInteger(0);
			this.maxConn = new AtomicInteger(maxConn);
		}

		public AtomicInteger getCurCount() {
			return this.curCount;
		}

		public void setCurCount(AtomicInteger curCount) {
			this.curCount = curCount;
		}

		public AtomicInteger getCurSec() {
			return this.curSec;
		}

		public void setCurSec(AtomicInteger curSec) {
			this.curSec = curSec;
		}

		public AtomicInteger getCurMsec() {
			return this.curMsec;
		}

		public void setCurMsec(AtomicInteger curMsec) {
			this.curMsec = curMsec;
		}

		public AtomicInteger getCurConn() {
			return this.curConn;
		}

		public void setCurConn(AtomicInteger curConn) {
			this.curConn = curConn;
		}

		public AtomicInteger getMaxConn() {
			return this.maxConn;
		}

		public void setMaxConn(AtomicInteger maxConn) {
			this.maxConn = maxConn;
		}

		public String getVaspid() {
			return this.vaspid;
		}

		public AtomicInteger getMaxCount() {
			return this.maxCount;
		}
	}
}
