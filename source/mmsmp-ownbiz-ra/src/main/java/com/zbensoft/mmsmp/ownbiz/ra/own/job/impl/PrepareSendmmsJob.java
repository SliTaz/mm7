package com.zbensoft.mmsmp.ownbiz.ra.own.job.impl;

import com.zbensoft.mmsmp.ownbiz.ra.own.dao.JobDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.PreUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.job.Job;
import org.apache.log4j.Logger;

public class PrepareSendmmsJob extends Job {
    static final Logger logger = Logger.getLogger(PrepareSendmmsJob.class);
    JobDao jobDao;

    public PrepareSendmmsJob() {
    }

    public void doHandler() {
        long atTime = System.currentTimeMillis();
        PreUserEntity pue = this.jobDao.preMmsUsers();
        logger.info(this.getJobName() + "[" + pue + ",execTime:" + (System.currentTimeMillis() - atTime) + "]");
    }

    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }
}
