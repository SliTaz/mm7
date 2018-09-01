package com.zbensoft.mmsmp.api;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.zbensoft.mmsmp.common.util.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestSeqTest {
	
	
	@Test
	public void test() throws Exception {

		
			Date startTime = DateUtil.convertStringToDate("2018-03-14 11:06:00", DateUtil.DATE_FORMAT_FIVE);
			
			System.out.println(DateUtil.convertDateToFormatString(new Date()));
			try {
				for (int i = 0; i < 10; i++) {
					new SeqTestThread("testSeqThread" + i, 100, startTime).start();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 
			while (true) {
				
				Thread.sleep(50000);
			}
	
	}
}
