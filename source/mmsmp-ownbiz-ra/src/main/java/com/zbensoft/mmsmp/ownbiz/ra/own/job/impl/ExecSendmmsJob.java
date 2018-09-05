package com.zbensoft.mmsmp.ownbiz.ra.own.job.impl;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.JobDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.MmsUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.job.Job;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.FileSize;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.MmsUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.PropertiesUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ExecSendmmsJob extends Job {
    static final Logger logger = Logger.getLogger(ExecSendmmsJob.class);
    static String path = ConfigUtil.getInstance().getCommonConfig().getContentFilePath();
    public static final int FIRST_NUM = PropertiesUtil.getInt("common", "first_num");
    public static final int SECOND_NUM = PropertiesUtil.getInt("common", "second_num");
    public static final int THIRD_NUM = PropertiesUtil.getInt("common", "third_num");
    JobDao jobDao;
    MmsUtil mmsUtil;
    int fetchRows;
    int sendPerSecond = 0;
    Object locker = new Object();
    List<MmsUserEntity> preFetchSends = null;
    boolean preFetchRunFlag = false;

    public ExecSendmmsJob() {
    }

    public void doHandler() {
        this.sendPerSecond = this.sendPerSecond <= 0 ? 100 : this.sendPerSecond;
        this.fetchRows = this.fetchRows <= 0 ? 100 : this.fetchRows;
        long atTime = System.currentTimeMillis();
        int sendFlag;
        int oddusers;
        boolean run = true;
        Long contentId = this.jobDao.selectContentIDInSend();
        if (contentId != null) {
            int begin = 1;
            MmsUserEntity mmsUserEntity = this.jobDao.getSendmmsUserEntity(contentId);
            long fileSize = 0L;
            if (mmsUserEntity != null) {
                fileSize = FileSize.fileSize(path + mmsUserEntity.getContentFile());
            }

            if (fileSize < 92160L) {
                this.sendPerSecond = FIRST_NUM;
            } else if (fileSize < 204800L) {
                this.sendPerSecond = SECOND_NUM;
            } else {
                this.sendPerSecond = THIRD_NUM;
            }

            logger.info("sendPerSecond:" + this.sendPerSecond);
            List<MmsUserEntity> sends = this.jobDao.getSendmmsUsers(contentId, begin, this.fetchRows);
            begin = begin + sends.size();

            try {
                while(run) {
                    if (this.preFetchSends == null && !this.isPreFetchRunFlag()) {
                        Thread preFetchThread = new Thread(new ExecSendmmsJob.PreFetchJob(contentId, begin, this.fetchRows));
                        preFetchThread.start();
                    }

                    if (sends.size() <= 0) {
                        int dataInTable = this.jobDao.getSendmmsCount(contentId);
                        if (dataInTable + 1 == begin) {
                            this.jobDao.deleteSendMMSUsersByContentID(contentId);
                            this.jobDao.updateSendmmsFlag(contentId);
                            this.preFetchSends = null;
                            this.preFetchRunFlag = false;
                            run = false;
                        }
                    } else {
                        MmsUserEntity massuser = (MmsUserEntity)sends.get(0);
                        StringBuilder phones = new StringBuilder();
                        List<MmsUserEntity> toRemove = new ArrayList();

                        for(int i = 0; i < sends.size() && i < this.sendPerSecond; ++i) {
                            MmsUserEntity send = (MmsUserEntity)sends.get(i);
                            toRemove.add(send);
                            if (i != 0) {
                                phones.append(",");
                            }

                            phones.append(send.getUserNumber());
                        }

                        sends.removeAll(toRemove);
                        massuser.setUserNumber(phones.toString());

                        for(sendFlag = this.mmsUtil.sendMms(massuser, (String)null); sendFlag != 0; sendFlag = this.mmsUtil.sendMms(massuser, (String)null)) {
                            Thread.sleep(1000L);
                        }

                        if (sends.size() == 0) {
                            if (this.preFetchSends == null) {
                                Object var23 = this.locker;
                                synchronized(this.locker) {
                                    this.locker.wait();
                                }
                            }

                            sends = this.preFetchSends;
                            begin += sends.size();
                            this.preFetchSends = null;
                        }

                        Thread.sleep(1000L);
                        logger.debug("execute send mms job[contentid:" + massuser.getContentId() + ",sendusers:" + toRemove.size() + ",sendflag:" + (sendFlag == 0 ? "success" : "fail") + ",exectime:" + (System.currentTimeMillis() - atTime) + ",productId=" + massuser.getProductId() + "]");
                    }
                }
            } catch (InterruptedException var18) {
                var18.printStackTrace();
            }

        }
    }

    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    public void setMmsUtil(MmsUtil mmsUtil) {
        this.mmsUtil = mmsUtil;
    }

    public void setFetchRows(int fetchRows) {
        this.fetchRows = fetchRows;
    }

    public synchronized boolean isPreFetchRunFlag() {
        return this.preFetchRunFlag;
    }

    public synchronized void setPreFetchRunFlag(boolean preFetchRunFlag) {
        this.preFetchRunFlag = preFetchRunFlag;
    }

    public int getSendPerSecond() {
        return this.sendPerSecond;
    }

    public void setSendPerSecond(int sendPerSecond) {
        this.sendPerSecond = sendPerSecond;
    }

    class PreFetchJob implements Runnable {
        Long contentId;
        int begin;
        int size;

        public PreFetchJob(Long contentId, int begin, int size) {
            this.contentId = contentId;
            this.begin = begin;
            this.size = size;
        }

        public void run() {
            ExecSendmmsJob.this.setPreFetchRunFlag(true);
            ExecSendmmsJob.this.preFetchSends = ExecSendmmsJob.this.jobDao.getSendmmsUsers(this.contentId, this.begin, this.size);
            ExecSendmmsJob.this.setPreFetchRunFlag(false);
            Object var1 = ExecSendmmsJob.this.locker;
            synchronized(ExecSendmmsJob.this.locker) {
                ExecSendmmsJob.this.locker.notify();
            }
        }
    }
}
