package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.threadPool;

import org.apache.log4j.Logger;

public class Channel {
    protected static final int MAX = 10;
    protected final Request[] requestQ = new Request[10];
    protected int tail = 0;
    protected int head = 0;
    protected int count = 0;
    protected final WorkerThread[] threadPool;
    private Logger log = Logger.getLogger(Channel.class);

    public Channel(int threads) {
        this.threadPool = new WorkerThread[threads];

        for(int i = 0; i < this.threadPool.length; ++i) {
            this.threadPool[i] = new WorkerThread("worker-" + i, this);
        }

    }

    public Channel(int threads, Class clazz) throws InstantiationException, IllegalAccessException {
        this.threadPool = new WorkerThread[threads];

        for(int i = 0; i < this.threadPool.length; ++i) {
            this.threadPool[i] = (WorkerThread)clazz.newInstance();
        }

    }

    public void startWorkers() {
        for(int i = 0; i < this.threadPool.length; ++i) {
            this.threadPool[i].start();
        }

    }

    public synchronized void putRequest(Request request) {
        while(this.count >= this.requestQ.length) {
            try {
                this.log.error("超过缓冲大小，请求被丢弃,缓冲大小：" + this.requestQ.length + "缓冲中所装的对象" + this.requestQ[0]);
                return;
            } catch (Exception var3) {
                ;
            }
        }

        this.requestQ[this.tail] = request;
        this.tail = (this.tail + 1) % this.requestQ.length;
        ++this.count;
        this.log.info(this.requestQ[0].getClass() + " channael size is :" + this.count);
        this.notifyAll();
    }

    public synchronized Request takeRequest() {
        while(this.count <= 0) {
            try {
                this.wait();
            } catch (Exception var2) {
                ;
            }
        }

        Request request = this.requestQ[this.head];
        this.head = (this.head + 1) % this.requestQ.length;
        --this.count;
        this.log.info(this.requestQ[0].getClass() + " channael size is :" + this.count);
        this.notifyAll();
        return request;
    }
}
