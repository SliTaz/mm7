
package com.zbensoft.mmsmp.ownbiz.ra.own.job;

public abstract class Job {
    protected String jobName = "";

    public Job() {
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return this.jobName;
    }

    public abstract void doHandler();
}
