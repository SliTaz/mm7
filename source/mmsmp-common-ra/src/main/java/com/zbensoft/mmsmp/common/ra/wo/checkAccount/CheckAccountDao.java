package com.zbensoft.mmsmp.common.ra.wo.checkAccount;

import com.zbensoft.mmsmp.common.ra.wo.hibernate.StatisticsBilling;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CheckAccountDao extends HibernateDaoSupport {
	public boolean isExistedStatisticsBilling(String sql) {
		Session session = getSession();
		SQLQuery query = getSession().createSQLQuery(sql);
		List list = query.list();
		releaseSession(session);
		int count = -1;
		if ((list != null) && (list.size() != 0) && (list.get(0) != null)) {
			count = ((Integer) list.get(0)).intValue();
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

	public void add(StatisticsBilling billing) {
		getSession().saveOrUpdate(billing);
	}
}
