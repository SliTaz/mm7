package com.zbensoft.mmsmp.common.ra.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.sql.ResultSet;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.bo.FileInfo;
import com.zbensoft.mmsmp.common.ra.bo.OrderRelationUpdateInfo;
import com.zbensoft.mmsmp.common.ra.util.DBUtil;
import com.zbensoft.mmsmp.common.ra.util.FtpServer;
import com.zbensoft.mmsmp.common.ra.util.ReadConfig;
import com.zbensoft.mmsmp.common.ra.util.XMLUtil;

public class FtpScanApp extends Thread {
	private static final String ftpRoot = ReadConfig.getFtpPath();
	private static final String user = ReadConfig.getUser();
	private static final String localPath = ReadConfig.getLocalPath();

	private static final Logger log = Logger.getLogger(FtpScanApp.class);
	private static final String REQNAME = "SP_SUBSCRIPTIONREQ";
	private static final String RSPNAME = "SP_SUBSCRIPTIONRSP";

	public void run() {
		while (true) {
			FtpServer ftpServer = new FtpServer(ReadConfig.getServer());
			try {
				ftpServer.connect(ReadConfig.getUser(), ReadConfig.getPasswd());

				log.info("login to ftp server success!");
				ftpServer.changeDir(ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONREQ" + "/");

				FTPFile[] files = ftpServer.getFtp().listFiles();

				for (int i = 0; i < files.length; i++) {
					log.info("=======" + files[i].getName());
					if (!files[i].isDirectory()) {
						String fileName = files[i].getName();
						if (isValidFile(fileName)) {
							log.info("begin to download file:   " + fileName);

							OutputStream out = new FileOutputStream(localPath + fileName);
							ftpServer.getFtp()
									.retrieveFile(ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONREQ" + "/" + fileName, out);
							out.close();
							log.info("-----------------download file success! ------------------  ");

							log.info("begin to do business !");

							FileInfo fileInfo = XMLUtil.parseReqFile(fileName);
							if (fileInfo == null)
								continue;
							int resultCode = doOrderRelation(fileInfo);
							log.info("-----------fileInfo contents-------\n  streamingNo: " + fileInfo.getStreamingNo()
									+ "   timestamp :  " + fileInfo.getTimkeStamp());
							String resFileName = XMLUtil.createResponseFile(fileName, resultCode,
									fileInfo.getStreamingNo());
							InputStream in = new FileInputStream(new File(ReadConfig.getLocalPath() + resFileName));

							log.info("path:  " + ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONRSP" + "/" + "              "
									+ ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONREQ" + "/");

							ftpServer.uploadFile(ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONRSP" + "/", in, resFileName);
							in.close();
							log.info(ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONREQ" + "/" + fileName);
							boolean flag = ftpServer
									.deleteFile(ReadConfig.getFtpPath() + "SP_SUBSCRIPTIONREQ" + "/" + fileName);
							log.info("delete remote req file success!");

							log.info(fileName + "    have all ready finished!");
							log.info("begin to delete local file  : " + fileName);
							File file = new File(localPath + fileName);
							if (file.exists()) {
								file.delete();
								log.info("delete local file : " + fileName + " success!");
							}

							file = new File(localPath + resFileName);
							if (file.exists()) {
								file.delete();
								log.info("delete local file : " + resFileName + " success!");
							}

						}

					}

				}

				ftpServer.logout();
				log.error("begin to sleep   :" + ReadConfig.getInterval() + "ms");
				try {
					Thread.sleep(Long.parseLong(ReadConfig.getInterval()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isValidFile(String fileName) {
		return (fileName.startsWith("SPSUBSCRIPTION")) && (fileName.endsWith(".REQ"));
	}

	public static void main(String[] argv) {
	}

	public FileInfo parseFiles(FTPFile file) {
		FileInfo fileInfo = new FileInfo();
		return fileInfo;
	}

	private boolean isNull(String str) {
		if (str == null)
			return true;
		return str.trim().length() == 0;
	}

	public int doOrderRelation(FileInfo file) {
		int resultCode = 0;
		String streamingNo = file.getStreamingNo();
		String timestamp = file.getTimkeStamp();
		OrderRelationUpdateInfo[] orderInfos = file.getOrderRelationUpdateInfo();
		log.info("-------------------begin to deal with the orderRelationUpdateInfo---------------");
		log.info("streamingNo :    " + streamingNo);
		log.info("timestamp :      " + timestamp);

		for (OrderRelationUpdateInfo orderInfo : orderInfos) {
			String subStreamingNo = orderInfo.getSUBStreamingNo();
			String userId = orderInfo.getUserID();
			int userIdType = orderInfo.getUserIDType().intValue();
			String productId = orderInfo.getProductID();
			String packageId = orderInfo.getPackageID();
			int opType = orderInfo.getOPType().intValue();

			boolean isPackage = false;
			if ((orderInfo.getPackageID() != null) && (orderInfo.getPackageID().trim().length() > 0)) {
				isPackage = true;
			}

			String segSql = " select code from province where code in (select province from mobile_segment where  segment = substr('"
					+ userId + "',0,7) )";

			log.info(segSql);

			String provinceCode = DBUtil.queryProvinceCode(segSql);

			String temproductid = "";
			if (!isNull(productId))
				temproductid = productId;
			else {
				temproductid = packageId;
			}
			StringBuilder sqlSb = new StringBuilder(
					"select FEETYPE, ORDERFEE, UNIQUEID, PRODUCT_TYPE,ispackage from VASSERVICE where SERVICECODE = '"
							+ temproductid + "'");
			log.info(sqlSb.toString());
			try {
				ResultSet rs = DBUtil.query(sqlSb.toString());
				if (rs.next()) {
					String feeType = rs.getString("feetype");
					String uniqueId = rs.getString("uniqueid");
					String productType = rs.getString("product_type");
					String isPack = rs.getString("ispackage");
					String orderfee = rs.getString("orderfee");
					log.info("here no error!!");
					if (opType == 0) {
						if (!DBUtil.checkExist("select * from user_info where cellphonenumber = '" + userId + "'")) {
							new DBUtil().insertUserInfo(userId);
						}

						String deleteSql = "delete from user_order where cellphonenumber='" + userId
								+ "' and serviceuniueid = '" + uniqueId + "'";
						log.info(deleteSql);
						DBUtil.executeUpdate(deleteSql);
						String addSql = "insert into user_order_his (UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD,FEETYPE, FEE) values (USER_ORDER_HIS_SEQ.nextval,'"
								+ userId + "'" + " , '" + userId + "','" + uniqueId + "',sysdate,1,'" + feeType + "','"
								+ orderfee + "')";

						log.info(addSql);
						DBUtil.executeUpdate(addSql);

						String queryString = "select UNIQUEID from USER_ORDER_HIS where CELLPHONENUMBER = '" + userId
								+ "' and SERVICEUNIQUEID = " + uniqueId + " order by ORDERDATE desc";
						log.info(queryString);

						rs = DBUtil.query(queryString);
						if (rs.next()) {
							int orderHisId = rs.getInt(1);

							String sqlOrder = "insert into USER_ORDER(CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, ORDERHISID, STATUS,userarea,priority) values('"
									+ userId + "', '" + userId + "', " + uniqueId + ", sysdate, '1', '" + feeType
									+ "', " + orderfee + ", " + orderHisId + ", 0 ,'" + provinceCode + "',2)";
							log.info(sqlOrder);
							DBUtil.executeUpdate(sqlOrder);
						}
						rs.close();
					} else if (opType == 1) {
						String updateSql = "update USER_ORDER set STATUS = 1 where CELLPHONENUMBER = '" + userId
								+ "' and SERVICEUNIQUEID = " + uniqueId + " and status = 0";

						DBUtil.executeUpdate(updateSql);
					} else if (opType == 2) {
						String updateSql = "update USER_ORDER set STATUS = 0 where CELLPHONENUMBER = '" + userId
								+ "' and SERVICEUNIQUEID = " + uniqueId + " and status = 1";

						DBUtil.executeUpdate(updateSql);
					} else if (opType == 3) {
						String querySql = "select ORDERHISID from USER_ORDER where CELLPHONENUMBER = '" + userId
								+ "' and SERVICEUNIQUEID = " + uniqueId;
						ResultSet rs2 = DBUtil.query(querySql);
						if (rs2.next()) {
							int hisid = rs.getInt(1);
							rs2.close();
							String updateSql = "update USER_ORDER_HIS set CANCELORDERDATE = sysdate, CANCELORDERMETHOD = '1' where UNIQUEID = "
									+ hisid;
							DBUtil.executeUpdate(updateSql);

							String delSql = "delete from USER_ORDER where CELLPHONENUMBER = '" + userId
									+ "' and SERVICEUNIQUEID = " + uniqueId;
							DBUtil.executeUpdate(delSql);
						}
					} else if (opType == 4) {
						String sql = "select ORDERHISID from USER_ORDER where CELLPHONENUMBER = '" + userId;

						ResultSet rs3 = DBUtil.query(sql);
						if (!rs3.next())
							continue;
						String updateSql = "update USER_ORDER_HIS set CANCELORDERDATE = sysdate, CANCELORDERMETHOD = '1' where UNIQUEID = "
								+ rs3.getString(1);
						rs3.close();
						DBUtil.executeUpdate(updateSql);

						String delSql = "delete from USER_ORDER where CELLPHONENUMBER = '" + userId + "'";

						DBUtil.executeUpdate(delSql);
					} else if (opType == 5) {
						String updateSql = "update USER_ORDER set status = 1 where CELLPHONENUMBER = '" + userId + "'";

						DBUtil.executeUpdate(updateSql);
					} else {
						resultCode = 20;
						log.info("没有该种操作类型！");
					}
				} else {
					if (isPackage)
						resultCode = 15;
					else {
						resultCode = 14;
					}

					log.info("产品、套餐不存在！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("error occurs:     " + e.getMessage());
				return 99;
			}

		}

		return resultCode;
	}
}
