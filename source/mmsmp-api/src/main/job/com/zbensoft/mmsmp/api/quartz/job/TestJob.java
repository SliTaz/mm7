package com.zbensoft.mmsmp.api.quartz.job;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbensoft.mmsmp.api.alarm.util.MailUtil;

/**
 * 测试
 * 
 * 0 10 0/2 * * ?
 * 
 * @author xieqiang
 *
 */
public class TestJob implements Job {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<String> toList = new ArrayList<String>();
		toList.add("812515376@qq.com");
		toList.add("xie.qiang@ztesoft.com");
		toList.add("tony.xie@zbensoft.com");
		toList.add("mrcrazy1991@hotmail.com");
		
		File testFile=new File("/zte/tools/E-Payment_service_statistics_venezuela_2017-12-26.xls");
		try {
			MailUtil.sendEmail(MailUtil.EPAY_FILE_NAME, "cantv-epay Diseño", toList, null, null, "password is change to 111111 Diseño",testFile);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TASK_LOG.INFO("now Date is:"+DateUtil.convertDateToFormatString(new Date()));
	}
}
