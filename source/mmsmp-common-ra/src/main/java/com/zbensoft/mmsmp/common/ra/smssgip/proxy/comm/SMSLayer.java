package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class SMSLayer {
    public static final int maxId = 2147483647;
    protected int id;
    protected SMSLayer parent;
    protected HashMap children;
    private List listeners;
    private int nextChildId;
    public static Object mutex = new Object();

    protected SMSLayer(SMSLayer theParent) {
        if (theParent != null) {
            Object var2 = mutex;
            synchronized(mutex) {
                this.id = ++theParent.nextChildId;
            }

            if (theParent.nextChildId >= 2147483647) {
                theParent.nextChildId = 0;
            }

            if (theParent.children == null) {
                theParent.children = new HashMap();
            }

            theParent.children.put(new Integer(this.id), this);
            this.parent = theParent;
        }

    }

    public abstract void send(Object var1) throws SMSException;

    public void onReceive(Object message) {
        int childId = this.getChildId(message);
        SMSLayer child;
        if (childId == -1) {
            child = this.createChild();
            child.onReceive(message);
            this.fireEvent(new Event(2, this, child));
            child.close();
        } else {
            child = (SMSLayer)this.children.get(new Integer(this.getChildId(message)));
            if (child == null) {
                this.fireEvent(new Event(64, this, message));
            } else {
                child.onReceive(message);
                this.fireEvent(new Event(32, this, message));
            }
        }

    }

    public SMSLayer getParent() {
        return this.parent;
    }

    public int getChildNumber() {
        return this.children == null ? 0 : this.children.size();
    }

    protected SMSLayer createChild() {
        throw new UnsupportedOperationException("Not implement");
    }

    protected int getChildId(Object message) {
        throw new UnsupportedOperationException("Not implement");
    }

    public void close() {
        if (this.parent == null) {
            throw new UnsupportedOperationException("Not implement");
        } else {
            this.parent.children.remove(new Integer(this.id));
        }
    }

    public void addEventListener(EventListener l) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }

        this.listeners.add(l);
    }

    public void removeEventListener(EventListener l) {
        this.listeners.remove(l);
    }

    protected void fireEvent(Event e) {
        if (this.listeners != null) {
            Iterator it = this.listeners.iterator();

            while(it.hasNext()) {
                ((EventListener)it.next()).handle(e);
            }

        }
    }
}
