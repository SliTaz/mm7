package com.zbensoft.mmsmp.common.ra.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class DianBo {
	public String service_code;
	public String user_phone;
	public String link_id;
	public String sequence_number;
	public int confirm_interval = -1;
	public long create_time = System.currentTimeMillis();
	public static List<DianBo> list = new ArrayList();
	private static final Logger logger = Logger.getLogger(DianBo.class);

	public static synchronized void save_linkid(String _service_code, String _user_phone, String _link_id) {
	}

	public static synchronized void save_sequence_num(String _service_code, String _user_phone, String _link_id, String _sequence_num, int _confirm_interval) {
		logger.info("=====>save seq _sequence_num:" + _sequence_num);
		DianBo db = new DianBo();
		db.service_code = _service_code;
		db.user_phone = _user_phone;
		db.link_id = _link_id;
		db.sequence_number = _sequence_num;
		db.confirm_interval = _confirm_interval;
		list.add(db);
	}

	public static synchronized String get_sequence_number(String _service_code, String _user_phone) {
		logger.info("=====>get seq _sequence_num list size is:" + list.size());
		for (int a = 0; a < list.size(); a++) {
			DianBo db = (DianBo) list.get(a);
			logger.info("list " + a + " date:");
			logger.info("service_code= " + db.service_code);
			logger.info("create_time= " + db.create_time);
			logger.info("confirm_interval= " + db.confirm_interval);
			logger.info("link_id= " + db.link_id);
			logger.info("user_phone= " + db.user_phone);
			logger.info("sequence_number= " + db.sequence_number);
		}
		long now = System.currentTimeMillis();
		for (Iterator it = list.iterator(); it.hasNext();) {
			DianBo o = (DianBo) it.next();
			if ((_service_code.equals(o.service_code)) && (_user_phone.equals(o.user_phone)) && (o.link_id != null) && (o.sequence_number != null)) {
				it.remove();
				return o.sequence_number;
			}

			if (o.confirm_interval > 0) {
				if (now - o.confirm_interval > o.create_time) {
					it.remove();
				}
			} else if (now - 7200000L > o.create_time)
				it.remove();
		}

		return null;
	}
}
