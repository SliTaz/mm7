package com.zbensoft.mmsmp.ftp.sysc.orderRelationsFtp;


import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.zbensoft.mmsmp.ftp.sysc.spftp.SpInfoDAO;
import com.zbensoft.mmsmp.ftp.util.FilesHelper;
import com.zbensoft.mmsmp.ftp.util.MyFtpTools;
import com.zbensoft.mmsmp.ftp.util.PropertiesHelper;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class SyncOrderRelationsHandleThread extends Thread {
    private static final Logger logger = Logger.getLogger(SyncOrderRelationsHandleThread.class);
    private static SyncOrderRelationsHandleThread _instance;
    private UserRelationDAO userRelationDAO;
    private MyFtpTools ftp;
    private SpInfoDAO spInfoDAO;
    long last_process_filestime = System.currentTimeMillis() - (long)(PropertiesHelper.getInt("last_process_filestime_orderRelation") * 24 * 3600) * 1000L;

    public void setSpInfoDAO(SpInfoDAO spInfoDAO) {
        this.spInfoDAO = spInfoDAO;
    }

    private SyncOrderRelationsHandleThread() {
        String ftp_orderrelation_server_ip = PropertiesHelper.getString("ftp_orderrelation_server_ip");
        int ftp_orderrelation_server_port = PropertiesHelper.getInt("ftp_orderrelation_server_port", 21);
        String ftp_orderrelation_login_username = PropertiesHelper.getString("ftp_orderrelation_login_username");
        String ftp_orderrelation_login_password = PropertiesHelper.getString("ftp_orderrelation_login_password");
        String ftp_server_dir = PropertiesHelper.getString("ftp_server_dir");
        this.ftp = new MyFtpTools(ftp_orderrelation_server_ip, ftp_orderrelation_server_port, ftp_orderrelation_login_username, ftp_orderrelation_login_password, ftp_server_dir);
    }

    public static synchronized void startup() {
        if (_instance == null) {
            _instance = new SyncOrderRelationsHandleThread();
        }

        _instance.start();
        logger.info("SyncOrderRelationsHandleThread start...");
    }

    public void run() {
        while(true) {
            long ftp_monitor_interval = (long)PropertiesHelper.getInt("ftp_monitor_interval");
            boolean ftp_monitor_thread_status = PropertiesHelper.getBoolean("ftp_monitor_thread_status");
            if (ftp_monitor_thread_status && this.downFtpFiles(PropertiesHelper.getString("ftp_server_dir"))) {
                this.handleFiles();
            }

            try {
                sleep(ftp_monitor_interval);
            } catch (InterruptedException var5) {
                ;
            }
        }
    }

    private void handleFiles() {
        List<File> list_files = this.getFileList();
        Iterator iterator = list_files.iterator();

        while(iterator.hasNext()) {
            File file = (File)iterator.next();
            this.handleFile(file);
        }

    }

    private List<File> getFileList() {
        File files = new File(PropertiesHelper.getString("ftp_local_dir"));
        File[] listFiles = files.listFiles(SyncOrderRelationsHandleThread.VacFileFilter.vff);
        List<File> list_files = Arrays.asList(listFiles);
        Collections.sort(list_files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return o1.lastModified() < o2.lastModified() ? -1 : 1;
            }
        });
        return list_files;
    }

    private void filter_ftpFiles(List<FTPFile> files) {
        this.sortbytime_ftpfiles(files);
        Iterator it = files.iterator();

        while(it.hasNext()) {
            FTPFile f = (FTPFile)it.next();
            if (this.last_process_filestime >= f.getTimestamp().getTimeInMillis()) {
                it.remove();
            }
        }

    }

    private void sortbytime_ftpfiles(List<FTPFile> files) {
        Collections.sort(files, new Comparator<FTPFile>() {
            public int compare(FTPFile o1, FTPFile o2) {
                long a = o1.getTimestamp().getTimeInMillis();
                long b = o1.getTimestamp().getTimeInMillis();
                return (int)(a - b >> 0);
            }
        });
    }

    private void test_view(List<FTPFile> files) {
        Iterator iterator = files.iterator();

        while(iterator.hasNext()) {
            FTPFile ftpFile = (FTPFile)iterator.next();
            logger.info(ftpFile.getName() + " => " + ftpFile.getTimestamp().getTimeInMillis());
        }

    }

    private boolean downFtpFiles(String ftp_server_dir) {
        String root_dir = PropertiesHelper.getString("ftp_local_dir");
        List spIdList = this.spInfoDAO.getSpId();

        try {
            this.ftp.connect(ftp_server_dir);
            List<FTPFile> files = this.ftp.listFiles();
            this.filter_ftpFiles(files);
            if (files.size() < 1) {
                return false;
            }

            Iterator iterator = files.iterator();

            while(iterator.hasNext()) {
                FTPFile file = (FTPFile)iterator.next();
                if (file.isFile()) {
                    this.ftp.downFile(file, root_dir);
                    this.last_process_filestime = file.getTimestamp().getTimeInMillis();
                } else if (file.isDirectory()) {
                    for(int i = 0; i < spIdList.size(); ++i) {
                        if (file.getName().equals(spIdList.get(i))) {
                            logger.info("spId ====" + (String)spIdList.get(i));
                            this.ftp.close();
                            this.downFtpFiles(ftp_server_dir.endsWith("/") ? ftp_server_dir + file.getName() : ftp_server_dir + "/" + file.getName());
                        }
                    }
                }
            }

            return true;
        } catch (Exception var11) {
            logger.error("downFtpFiles() - exception e=", var11);
        } finally {
            this.ftp.close();
        }

        return false;
    }

    private void handleFile(File file) {
        List spIdList = this.spInfoDAO.getSpId();

        try {
            List<OrderRelation> ors = OrderRelation.parseFile(file, spIdList);
            this.userRelationDAO.saveRelations(ors);
            this.backupFile(file, ors != null && ors.size() != 0 && ors.get(0) != null ? ((OrderRelation)ors.get(0)).spId : null);
        } catch (Exception var5) {
            logger.error("handleFile(" + file + ") - Exception e=" + var5);
        }

    }

    private void backupFile(File file, String spId) {
        String bak_dir = PropertiesHelper.getString("ftp_local_backupdir");
        File bak_file = new File(bak_dir + File.separatorChar + spId + File.separatorChar + file.getName());
        File sp_dir = new File(bak_dir + File.separatorChar + spId + File.separatorChar);
        if (!sp_dir.exists()) {
            sp_dir.mkdirs();
        }

        FilesHelper.backupOrderFile(file, bak_file);
        logger.info("bak_dir is:" + sp_dir);
        String sp_bak_dir = PropertiesHelper.getString("ftp_sp_backupdir");
        File sp_bak_file = new File(sp_bak_dir + File.separatorChar + spId + File.separatorChar + file.getName());
        File sp1_dir = new File(sp_bak_dir + File.separatorChar + spId + File.separatorChar);
        if (!sp1_dir.exists()) {
            sp1_dir.mkdirs();
        }

        FilesHelper.backupOrderFile(file, sp_bak_file);
        logger.info("sp_bak_dir is:" + sp1_dir);
        String local_dir = PropertiesHelper.getString("ftp_local_dir");
        File local_file = new File(local_dir + file.getName());
        local_file.delete();
        logger.info("file name is:" + local_dir + file.getName());
    }

    public static void main(String[] args) {
        (new SyncOrderRelationsHandleThread()).start();
    }

    public void setUserRelationDAO(UserRelationDAO userRelationDAO) {
        this.userRelationDAO = userRelationDAO;
    }

    private static class VacFileFilter implements FileFilter {
        static final SyncOrderRelationsHandleThread.VacFileFilter vff = new SyncOrderRelationsHandleThread.VacFileFilter();

        private VacFileFilter() {
        }

        public boolean accept(File file) {
            String name = file.getName();
            return name.startsWith("SubscribeInfo") && name.endsWith(".req");
        }
    }
}

