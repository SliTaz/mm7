package com.zbensoft.mmsmp.common.ra.wo.checkAccount;

import com.zbensoft.mmsmp.common.ra.wo.hibernate.StatisticsBilling;

public class CheckAccountService {
	private CheckAccountDao checkAccountDao;

	public CheckAccountDao getCheckAccountDao() {
		return this.checkAccountDao;
	}

	public void setCheckAccountDao(CheckAccountDao checkAccountDao) {
		this.checkAccountDao = checkAccountDao;
	}

	public boolean isExistedStatisticsBilling(String sequenceId) {
		String sql = "select count(*) from statistics_billing where sp_orderid = '" + sequenceId + "'";
		return this.checkAccountDao.isExistedStatisticsBilling(sql);
	}

	public void add(StatisticsBilling billing) {
		this.checkAccountDao.add(billing);
	}
}
