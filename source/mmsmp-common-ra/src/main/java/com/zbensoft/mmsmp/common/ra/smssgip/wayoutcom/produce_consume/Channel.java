package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume;

import java.util.LinkedList;

public class Channel {
    private LinkedList list = new LinkedList();
    private int size;
    private boolean isValid = true;

    public Channel(int size) {
        this.size = size;
    }

    public synchronized void put(Object obj) throws InterruptedException {
        while(this.list.size() >= this.size) {
            this.wait();
        }

        this.list.addLast(obj);
        this.notifyAll();
    }

    public synchronized boolean put(Object obj, int timeout) throws InterruptedException {
        if (this.list.size() >= this.size) {
            this.wait((long)timeout);
            if (this.list.size() >= this.size) {
                return false;
            }
        }

        this.list.addLast(obj);
        this.notifyAll();
        return true;
    }

    public synchronized Object remove() throws InterruptedException {
        while(this.list.size() <= 0) {
            this.wait();
        }

        this.notifyAll();
        return this.list.removeFirst();
    }

    public synchronized Object remove(int timeout) throws InterruptedException {
        if (this.list.size() <= 0) {
            this.wait((long)timeout);
            if (this.list.size() <= 0) {
                return null;
            }
        }

        this.notifyAll();
        return this.list.removeFirst();
    }

    public synchronized Object[] removeAll() throws InterruptedException {
        while(this.list.size() <= 0) {
            this.wait();
        }

        Object[] obj = this.list.toArray();
        this.list.clear();
        this.notifyAll();
        return obj;
    }

    public synchronized Object get() {
        Object obj = null;
        if (this.list.size() > 0) {
            obj = this.list.getFirst();
        }

        return obj;
    }

    public synchronized Object[] getAll() {
        Object[] obj = this.list.toArray();
        return obj;
    }

    public synchronized int getSize() {
        return this.list.size();
    }

    public synchronized void setValid(boolean valid) {
        this.isValid = valid;
    }

    public synchronized boolean getValid() {
        return this.isValid;
    }
}

