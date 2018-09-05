package com.zbensoft.mmsmp.ownbiz.ra.base;

import com.zbensoft.mmsmp.common.ra.common.util.ApplicationContextFactory;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.JobDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.PreUserEntity;
import java.io.PrintStream;

public class DaoTestCase {
	public static void main(String[] args) {
		JobDao dao = (JobDao) ApplicationContextFactory.getBean("jobDao");
		PreUserEntity pue = dao.preMmsUsers();
		System.out.println(pue.getContentId());
		System.out.println(pue.getUsers());
		System.out.println(pue.getCode());
	}
}
