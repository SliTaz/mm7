package com.zbensoft.mmsmp.common.ra.smssgip.proxy.util;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
    public static final Parameter EMPTY = (new Parameter()).lock();
    boolean locked;
    Map args;

    public Parameter() {
        this(new HashMap());
    }

    public Parameter(Map theArgs) {
        if (theArgs == null) {
            throw new NullPointerException("argument is null");
        } else {
            this.args = theArgs;
        }
    }

    public String get(String key, String def) {
        try {
            String s = this.args.get(key).toString();
            return s;
        } catch (Exception var5) {
            return def;
        }
    }

    public int get(String key, int def) {
        try {
            int i = Integer.parseInt(this.args.get(key).toString());
            return i;
        } catch (Exception var5) {
            return def;
        }
    }

    public long get(String key, long def) {
        try {
            long l = Long.parseLong(this.args.get(key).toString());
            return l;
        } catch (Exception var7) {
            return def;
        }
    }

    public float get(String key, float def) {
        try {
            float f = Float.parseFloat(this.args.get(key).toString());
            return f;
        } catch (Exception var5) {
            return def;
        }
    }

    public boolean get(String key, boolean def) {
        try {
            boolean flag = "true".equals(this.args.get(key));
            return flag;
        } catch (Exception var5) {
            return def;
        }
    }

    public Object get(String key, Object def) {
        try {
            Object obj = this.args.get(key);
            return obj == null ? def : obj;
        } catch (Exception var6) {
            return def;
        }
    }

    public Parameter set(String key, Object value) {
        if (this.locked) {
            throw new UnsupportedOperationException("Args have locked,can modify");
        } else {
            this.args.put(key, value);
            return this;
        }
    }

    public Parameter set(String key, int value) {
        if (this.locked) {
            throw new UnsupportedOperationException("Args have locked,can modify");
        } else {
            this.args.put(key, new Integer(value));
            return this;
        }
    }

    public Parameter set(String key, boolean value) {
        if (this.locked) {
            throw new UnsupportedOperationException("Args have locked,can modify");
        } else {
            this.args.put(key, new Boolean(value));
            return this;
        }
    }

    public Parameter set(String key, long value) {
        if (this.locked) {
            throw new UnsupportedOperationException("Args have locked,can modify");
        } else {
            this.args.put(key, new Long(value));
            return this;
        }
    }

    public Parameter set(String key, float value) {
        if (this.locked) {
            throw new UnsupportedOperationException("Args have locked,can modify");
        } else {
            this.args.put(key, new Float(value));
            return this;
        }
    }

    public Parameter set(String key, double value) {
        if (this.locked) {
            throw new UnsupportedOperationException("Args have locked,can modify");
        } else {
            this.args.put(key, new Double(value));
            return this;
        }
    }

    public Parameter lock() {
        this.locked = true;
        return this;
    }

    public String toString() {
        return this.args.toString();
    }
}
