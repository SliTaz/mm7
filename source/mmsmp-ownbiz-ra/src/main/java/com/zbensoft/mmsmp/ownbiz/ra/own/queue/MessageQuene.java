package com.zbensoft.mmsmp.ownbiz.ra.own.queue;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQuene {
    private static final Logger _log = Logger.getLogger(MessageQuene.class);
    private static int quencesize;
    public static MessageQuene messageQuene;
    private LinkedBlockingQueue<AbstractMessage> requestQuence = null;
    private LinkedBlockingQueue<AbstractMessage> resultQuence = null;
    private ConcurrentHashMap<String, JdbcDaoSupport> daoMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, ProxyPayMessage> proxyPayMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Long> payTimeControlMap = new ConcurrentHashMap();

    static {
        quencesize = AppContants.QUEUE_NUM;
        messageQuene = new MessageQuene(quencesize);
    }

    public MessageQuene(int num) {
        this.requestQuence = new LinkedBlockingQueue(num);
        this.resultQuence = new LinkedBlockingQueue(num);
    }

    public static MessageQuene getInstance() {
        MessageQuene var0 = messageQuene;
        synchronized(messageQuene) {
            if (messageQuene == null) {
                messageQuene = new MessageQuene(quencesize);
            }

            return messageQuene;
        }
    }

    public int getQuenceSize() {
        return quencesize;
    }

    public AbstractMessage takeRequestMessage() {
        try {
            LinkedBlockingQueue var1 = this.requestQuence;
            synchronized(this.requestQuence) {
                AbstractMessage message = (AbstractMessage)this.requestQuence.take();
                _log.debug(String.format("messageQuene 获取并移除上行请求消息队列（requestQuence）的头部的消息体. \n") + message);
                return message;
            }
        } catch (InterruptedException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public AbstractMessage takeResultMessage() {
        try {
            LinkedBlockingQueue var1 = this.resultQuence;
            synchronized(this.resultQuence) {
                AbstractMessage message = (AbstractMessage)this.resultQuence.take();
                _log.debug(String.format("messageQuene 获取并移除响应消息队列（requestQuence）的头部的消息体. \n") + message);
                return message;
            }
        } catch (InterruptedException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public void addDaoMap(String key, JdbcDaoSupport dao) {
        ConcurrentHashMap var3 = this.daoMap;
        synchronized(this.daoMap) {
            if (dao != null && key != null) {
                if (!this.daoMap.containsKey(key)) {
                    this.daoMap.put(key, dao);
                }
            }
        }
    }

    public JdbcDaoSupport getDao(String key) {
        ConcurrentHashMap var2 = this.daoMap;
        synchronized(this.daoMap) {
            if (key == null) {
                return null;
            } else {
                return this.daoMap.containsKey(key) ? (JdbcDaoSupport)this.daoMap.get(key) : null;
            }
        }
    }

    public void addPayTimeControl(String key, Long entity) {
        ConcurrentHashMap var3 = this.payTimeControlMap;
        synchronized(this.payTimeControlMap) {
            if (entity != null && key != null) {
                if (!this.payTimeControlMap.containsKey(key)) {
                    this.payTimeControlMap.put(key, entity);
                }
            }
        }
    }

    public Long removePayTimeControl(String key) {
        ConcurrentHashMap var2 = this.payTimeControlMap;
        synchronized(this.payTimeControlMap) {
            if (key == null) {
                return null;
            } else {
                return this.payTimeControlMap.containsKey(key) ? (Long)this.payTimeControlMap.remove(key) : null;
            }
        }
    }

    public Long getPayTimeControl(String key) {
        ConcurrentHashMap var2 = this.payTimeControlMap;
        synchronized(this.payTimeControlMap) {
            if (key == null) {
                return null;
            } else {
                return this.payTimeControlMap.containsKey(key) ? (Long)this.payTimeControlMap.get(key) : null;
            }
        }
    }

    public void addProxyPayMap(String key, ProxyPayMessage entity) {
        ConcurrentHashMap var3 = this.proxyPayMap;
        synchronized(this.proxyPayMap) {
            if (entity != null && key != null) {
                if (!this.proxyPayMap.containsKey(key)) {
                    this.proxyPayMap.put(key, entity);
                }
            }
        }
    }

    public ProxyPayMessage removeProxyPayMap(String key) {
        ConcurrentHashMap var2 = this.proxyPayMap;
        synchronized(this.proxyPayMap) {
            if (key == null) {
                return null;
            } else {
                return this.proxyPayMap.containsKey(key) ? (ProxyPayMessage)this.proxyPayMap.remove(key) : null;
            }
        }
    }

    public static int getQuencesize() {
        return quencesize;
    }

    public static void setQuencesize(int quencesize) {
        quencesize = quencesize;
    }

    public static MessageQuene getMessageQuene() {
        return messageQuene;
    }

    public static void setMessageQuene(MessageQuene messageQuene) {
        messageQuene = messageQuene;
    }

    public LinkedBlockingQueue<AbstractMessage> getRequestQuence() {
        return this.requestQuence;
    }

    public void setRequestQuence(LinkedBlockingQueue<AbstractMessage> requestQuence) {
        this.requestQuence = requestQuence;
    }

    public LinkedBlockingQueue<AbstractMessage> getResultQuence() {
        return this.resultQuence;
    }

    public void setResultQuence(LinkedBlockingQueue<AbstractMessage> resultQuence) {
        this.resultQuence = resultQuence;
    }

    public ConcurrentHashMap<String, JdbcDaoSupport> getDaoMap() {
        return this.daoMap;
    }

    public void setDaoMap(ConcurrentHashMap<String, JdbcDaoSupport> daoMap) {
        this.daoMap = daoMap;
    }

    public ConcurrentHashMap<String, ProxyPayMessage> getProxyPayMap() {
        return this.proxyPayMap;
    }

    public void setProxyPayMap(ConcurrentHashMap<String, ProxyPayMessage> proxyPayMap) {
        this.proxyPayMap = proxyPayMap;
    }

    public ConcurrentHashMap<String, Long> getPayTimeControlMap() {
        return this.payTimeControlMap;
    }

    public void setPayTimeControlMap(ConcurrentHashMap<String, Long> payTimeControlMap) {
        this.payTimeControlMap = payTimeControlMap;
    }
}
