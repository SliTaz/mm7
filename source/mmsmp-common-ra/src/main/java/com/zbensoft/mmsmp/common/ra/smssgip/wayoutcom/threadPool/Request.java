package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.threadPool;

import java.util.Random;

public abstract class Request {
    private String name;
    private int number;
    private static final Random random = new Random();

    public Request() {
    }

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public abstract void execute();

    public String toString() {
        return "[Request from " + this.name + "No. " + this.number + "]";
    }
}