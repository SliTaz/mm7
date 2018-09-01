package com.zbensoft.mmsmp.api.factory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.SpringBeanUtil;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;



public class ProvinceCityConfigFactory {
	private  static Logger log = Logger.getLogger(ProvinceCityConfigFactory.class);
	private  ProvinceCityService  provinceCityService=SpringBeanUtil.getBean(ProvinceCityService.class);

	Map<String,ProvinceCity> map = new ConcurrentHashMap<String,ProvinceCity>();
	
	private static ProvinceCityConfigFactory instance = new ProvinceCityConfigFactory();
	
	private static final Object uniqueLock = new Object();
	private static final Object objectLock = new Object();
	
	public static ProvinceCityConfigFactory getInstance(){
		if(instance==null){
		synchronized (uniqueLock) {
			if (null == instance) {
				instance = new ProvinceCityConfigFactory();
			}
		}
	}
	 return instance;
		
	}
	public void loadConfig() {
		try {
			Map<String, ProvinceCity> mapTmp= new ConcurrentHashMap<String, ProvinceCity>();
			PageHelper.startPage(1, 100000);
			List<ProvinceCity> provinceCityList = provinceCityService.selectPage(new ProvinceCity());
			if(provinceCityList!=null&&!provinceCityList.isEmpty()){
				for (ProvinceCity provinceCity : provinceCityList) {
					mapTmp.put(provinceCity.getProvinceCityId(), provinceCity);
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
	public Map<String, ProvinceCity> getMap() {
		return map;
	}
	public ProvinceCity getProvinceCityCofigByProvinceCityId(String provinceCityId) {
		return map.get(provinceCityId);
}
	

	

}
