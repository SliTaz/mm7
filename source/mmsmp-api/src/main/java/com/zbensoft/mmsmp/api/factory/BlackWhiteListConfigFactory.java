package com.zbensoft.mmsmp.api.factory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.SpringBeanUtil;
import com.zbensoft.mmsmp.api.service.api.BlackWhiteListService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;

public class BlackWhiteListConfigFactory {
	private  static Logger log = Logger.getLogger(BlackWhiteListConfigFactory.class);
	private  static BlackWhiteListService blackWhiteListService = SpringBeanUtil.getBean(BlackWhiteListService.class);
	
	Map<String,BlackWhiteList> map = new ConcurrentHashMap<String,BlackWhiteList>();
	
	public  static BlackWhiteListConfigFactory instance= new BlackWhiteListConfigFactory();
	
	private static final Object uniqueLock = new Object();
	private static final Object objectLock = new Object();
	
	public static BlackWhiteListConfigFactory getInstance(){
		if(instance==null){
		synchronized (uniqueLock) {
			if (null == instance) {
				instance = new BlackWhiteListConfigFactory();
			}
		}
	}
		return instance;
	}
	public void loadConfig() {
		try {
			Map<String, BlackWhiteList> mapTmp= new ConcurrentHashMap<String, BlackWhiteList>();
			PageHelper.startPage(1, 100000);
			List<BlackWhiteList> blackWhiteListList = blackWhiteListService.selectPage(new BlackWhiteList());
			if(blackWhiteListList!=null&&!blackWhiteListList.isEmpty()){
				for (BlackWhiteList blackWhiteList : blackWhiteListList) {
					mapTmp.put(blackWhiteList.getBlackWhiteList(), blackWhiteList);
				}
			}
			map=mapTmp;
			
		} catch (Exception e) {
			log.error("", e);
		}
}
	
	public Integer getMapSize() {
		return map.size();
	}
	public Map<String, BlackWhiteList> getMap() {
		return map;
	}
	public BlackWhiteList getBlackWhiteListByBlackWhiteList(String BlackWhiteList) {
		return map.get(BlackWhiteList);
}
	

}
