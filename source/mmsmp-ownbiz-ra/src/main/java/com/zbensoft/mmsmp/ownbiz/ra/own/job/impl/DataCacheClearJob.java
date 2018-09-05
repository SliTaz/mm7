
package com.zbensoft.mmsmp.ownbiz.ra.own.job.impl;

import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.job.Job;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataCacheClearJob extends Job {
    public static final Logger logger = Logger.getLogger(DataCacheClearJob.class);
    private static MessageQuene messageQuene = null;

    public DataCacheClearJob() {
    }

    public void doHandler() {
        ConcurrentHashMap ppmMap;
        Set keySet;
        Iterator it;
        long currentTime;
        String key;
        long value;
        try {
            if (messageQuene == null) {
                messageQuene = MessageQuene.getInstance();
            }

            logger.info("清理内存中垃圾数据开始。。。》》》》》》");
            ppmMap = messageQuene.getPayTimeControlMap();
            keySet = ppmMap.keySet();
            it = keySet.iterator();
            currentTime = System.currentTimeMillis();

            while(it.hasNext()) {
                key = (String)it.next();
                value = (Long)ppmMap.get(key);
                if (currentTime - value > AppContants.APP_REPEAT_REQUEST_INTERVAL) {
                    messageQuene.removePayTimeControl(key);
                }
            }
        } catch (Exception var10) {
            logger.error(var10.getMessage(), var10);
        }

        try {
            ppmMap = messageQuene.getProxyPayMap();
            keySet = ppmMap.keySet();
            it = keySet.iterator();
            currentTime = System.currentTimeMillis();

            while(it.hasNext()) {
                key = (String)it.next();
                value = ((ProxyPayMessage)ppmMap.get(key)).getPayRequestTime();
                if (currentTime - value > AppContants.PAY_REQUEST_MAX_KEEP_TIME) {
                    messageQuene.removeProxyPayMap(key);
                }
            }
        } catch (Exception var9) {
            logger.error(var9.getMessage(), var9);
        }

        logger.info("《《《《《《。。。清理内存中垃圾数据结束");
    }
}
