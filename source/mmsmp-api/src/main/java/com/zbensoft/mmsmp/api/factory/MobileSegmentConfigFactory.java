package com.zbensoft.mmsmp.api.factory;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.SpringBeanUtil;
import com.zbensoft.mmsmp.api.service.api.MobileSegmentService;
import com.zbensoft.mmsmp.db.domain.MobileSegment;



public class MobileSegmentConfigFactory {
	private  static Logger log = Logger.getLogger(MobileSegmentConfigFactory.class);
	private  static MobileSegmentService mobileSegmentService = SpringBeanUtil.getBean(MobileSegmentService.class);
	
	Map<String,MobileSegment> map = new ConcurrentHashMap<String,MobileSegment>();
	
	public  static MobileSegmentConfigFactory instance= new MobileSegmentConfigFactory();
	
	private static final Object uniqueLock = new Object();
	private static final Object objectLock = new Object();
	
	public static MobileSegmentConfigFactory getInstance(){
		if(instance==null){
		synchronized (uniqueLock) {
			if (null == instance) {
				instance = new MobileSegmentConfigFactory();
			}
		}
	}
		return instance;
	}
	public void loadConfig() {
		try {
			Map<String, MobileSegment> mapTmp= new ConcurrentHashMap<String, MobileSegment>();
			PageHelper.startPage(1, 100000);
			List<MobileSegment> mobileSegmentList = mobileSegmentService.selectPage(new MobileSegment());
			if(mobileSegmentList!=null&&!mobileSegmentList.isEmpty()){
				for (MobileSegment mobileSegment : mobileSegmentList) {
					mapTmp.put(mobileSegment.getMobileSegmentId(), mobileSegment);
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
	public Map<String, MobileSegment> getMap() {
		return map;
	}
	public MobileSegment getMobileSegmentCofigByMobileSegmentId(String mobileSegmentId) {
		return map.get(mobileSegmentId);
}
	

	

}
