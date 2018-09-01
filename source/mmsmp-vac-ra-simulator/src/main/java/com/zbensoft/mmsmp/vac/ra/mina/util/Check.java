package com.zbensoft.mmsmp.vac.ra.mina.util;

import java.util.Date;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.vac.ra.aaa.CheckPrice;
import com.zbensoft.mmsmp.vac.ra.aaa.CheckPriceConfirm;
import com.zbensoft.mmsmp.vac.ra.aaa.Common;
import com.zbensoft.mmsmp.vac.ra.mina.vac.VACSender;
import com.zbensoft.mmsmp.vac.ra.util.DateHelper;

public class Check {

	  private static final Logger logger = Logger.getLogger(Check.class);
	  public static final int ERR = -1;
	  public static final int FAIL = 1;
	  public static final int SUCCESS = 0;

	  public static void check_relation(String src_SequenceNumber, String user_number, String sp_product_id, String sp_id, Integer messageid, String threadname)
	  {
	    CheckPrice cp = new CheckPrice(messageid, threadname);
	    cp.setOperation_Type(CheckPrice.ORDER_RELATION);
	    cp.setServiceIDType(Integer.valueOf(3));
	    cp.setSP_IDorAccessNo(sp_id);
	    cp.setServiceIDorFeatureString(sp_product_id);
	    cp.setProductID(sp_product_id);
	    cp.setOA(user_number);
	    cp.setDA(user_number);
	    cp.setFA(user_number);
	    cp.setSequenceNumber(src_SequenceNumber);

	    VACSender sender = new VACSender(threadname);
	    sender.send(cp);
	  }

	  private static void log(int ret) {
	    String msg = (String)Common.ret_code.get(Integer.valueOf(ret));
	    logger.info("ret_code=[" + ret + "]" + msg);
	  }

	  public static void user_order(String src_SequenceNumber, String user_number, String sp_product_id, String sp_id, Integer messageid, String threadname)
	  {
	    CheckPrice cp = new CheckPrice(messageid, threadname);
	    cp.setOperation_Type(CheckPrice.ORDER);
	    cp.setServiceIDType(Integer.valueOf(3));
	    cp.setSP_IDorAccessNo(sp_id);
	    cp.setServiceIDorFeatureString(sp_product_id);
	    cp.setOA(user_number);
	    cp.setFA(user_number);
	    cp.setDA(user_number);
	    cp.setSequenceNumber(src_SequenceNumber);
	    cp.setOrderMethod("03");
	    logger.info(cp.toString());

	    VACSender sender = new VACSender(threadname);
	    sender.send(cp);
	  }

	  public static void user_unorder(String src_SequenceNumber, String user_number, String sp_product_id, String service_id, String sp_id, boolean is_all, Integer messageid, String threadname)
	  {
	    CheckPrice cp = new CheckPrice(messageid, threadname);
	    if (is_all) {
	      cp.setOperation_Type(CheckPrice.CANCEL_ALL);
	      cp.setServiceIDType(Integer.valueOf(3));
	      cp.setSP_IDorAccessNo("00000");
	      cp.setServiceIDorFeatureString("00000");
	    }
	    else {
	      cp.setOperation_Type(CheckPrice.CANCEL);
	      cp.setSP_IDorAccessNo(sp_id);
	      if (!"".equals(sp_product_id)) {
	        cp.setServiceIDType(Integer.valueOf(3));
	        cp.setServiceIDorFeatureString(sp_product_id);
	      } else if (!"".equals(service_id)) {
	        cp.setServiceIDType(Integer.valueOf(1));
	        cp.setServiceIDorFeatureString(service_id);
	      }
	    }
	    cp.setDA(user_number);
	    cp.setOA(user_number);
	    cp.setSequenceNumber(src_SequenceNumber);

	    VACSender sender = new VACSender(threadname);
	    sender.send(cp);
	  }

	  public static void user_onceorder(String src_SequenceNumber, String user_number, String sp_product_id, String sp_id, Integer messageid, String threadname)
	  {
	    CheckPrice cp = new CheckPrice(messageid, threadname);
	    cp.setOperation_Type(CheckPrice.ONCE_ORDER);
	    cp.setServiceIDType(Integer.valueOf(3));
	    cp.setSP_IDorAccessNo(sp_id);
	    cp.setServiceIDorFeatureString(sp_product_id);
	    cp.setOA(user_number);
	    cp.setFA(user_number);
	    cp.setDA(user_number);
	    cp.setSequenceNumber(src_SequenceNumber);

	    VACSender sender = new VACSender(threadname);
	    sender.send(cp);
	  }

	  public static void check_price(String src_SequenceNumber, String user_number, String service_id, String sp_id, String link_id, Integer servicedown_type, Integer messageid, String threadname)
	  {
	    CheckPrice cp = new CheckPrice(messageid, threadname);
	    cp.setOperation_Type(CheckPrice.AUTHENTICATE);
	    cp.setServiceIDType(Integer.valueOf(1));
	    cp.setSP_IDorAccessNo(sp_id);
	    cp.setServiceIDorFeatureString(service_id);
	    cp.setOA(user_number);
	    cp.setFA(user_number);
	    cp.setDA(user_number);
	    cp.setSequenceNumber(src_SequenceNumber);

	    if (servicedown_type != null) {
	      cp.setService_updown_Type(servicedown_type);
	    }
	    if (link_id != null) {
	      cp.setLinkID(link_id);
	    }
	    logger.info(cp.toString());

	    VACSender sender = new VACSender(threadname);
	    sender.send(cp);
	  }

	  public static void check_priceconfirm(String SequenceNumber, int result_code, Integer messageid, String threadname)
	  {
	    CheckPriceConfirm cpc = new CheckPriceConfirm(messageid, threadname);
	    cpc.setSequenceNumber(SequenceNumber);
	    cpc.setErrCode(Integer.valueOf(result_code));
	    cpc.setEnd_Time(getNow());
	    cpc.setServiceType("31");

	    logger.info("message[sequenceNumber:" + cpc.getSequenceNumber() + " sequenceId:" + cpc.getSequenceId() + "messageid:" + messageid + "]");
	    VACSender sender = new VACSender(threadname);
	    sender.send(cpc);
	  }

	  private static String getNow() {
	    return DateHelper.getyyyyMMddHHmmss(new Date());
	  }
}
