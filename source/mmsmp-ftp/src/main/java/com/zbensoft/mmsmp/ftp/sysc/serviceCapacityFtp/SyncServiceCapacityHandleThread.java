package com.zbensoft.mmsmp.ftp.sysc.serviceCapacityFtp;

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

public class SyncServiceCapacityHandleThread extends Thread {
    private static final Logger logger = Logger.getLogger(SyncServiceCapacityHandleThread.class);
    private static SyncServiceCapacityHandleThread _instance;
    private MyFtpTools ftp;
    private ServiceCapacityDAO serviceCapacityDAO = new ServiceCapacityDAO();
    private SpInfoDAO spInfoDAO;
    long last_process_filestime = System.currentTimeMillis() - (long)(PropertiesHelper.getInt("last_process_filestime_spsrvcapinfo") * 24 * 3600) * 1000L;

    public SpInfoDAO getSpInfoDAO() {
        return this.spInfoDAO;
    }

    public void setSpInfoDAO(SpInfoDAO spInfoDAO) {
        this.spInfoDAO = spInfoDAO;
    }

    public SyncServiceCapacityHandleThread() {
        String ftp_server_ip = PropertiesHelper.getString("ftp_server_ip");
        int ftp_server_port = PropertiesHelper.getInt("ftp_server_port", 21);
        String ftp_login_username = PropertiesHelper.getString("ftp_login_username");
        String ftp_login_password = PropertiesHelper.getString("ftp_login_password");
        String ftp_server_dir = PropertiesHelper.getString("ftp_spsrvcapinfo_server_dir");
        this.ftp = new MyFtpTools(ftp_server_ip, ftp_server_port, ftp_login_username, ftp_login_password, ftp_server_dir);
    }

    public static synchronized void startup() {
        if (_instance == null) {
            _instance = new SyncServiceCapacityHandleThread();
        }

        _instance.start();
        logger.info("SyncServiceCapacityHandleThread start...");
    }

    public void run() {
        while(true) {
            long ftp_monitor_interval = (long)PropertiesHelper.getInt("ftp_monitor_interval_spsrvcapinfo");
            boolean ftp_monitor_thread_status = PropertiesHelper.getBoolean("ftp_spsrvcapinfo_isrunning");
            if (ftp_monitor_thread_status && this.downFtpFiles(PropertiesHelper.getString("ftp_spsrvcapinfo_server_dir"))) {
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
        File files = new File(PropertiesHelper.getString("ftp_spsrvcapinfo_local_dir"));
        File[] listFiles = files.listFiles(SyncServiceCapacityHandleThread.VacFileFilter.vff);
        List<File> list_files = Arrays.asList(listFiles);
        Collections.sort(list_files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return o1.lastModified() < o2.lastModified() ? -1 : 1;
            }
        });
        return list_files;
    }

    private boolean downFtpFiles(String ftp_spsrvcapinfo_server_dir) {
        String root_dir = PropertiesHelper.getString("ftp_spsrvcapinfo_local_dir");

        try {
            this.ftp.connect(ftp_spsrvcapinfo_server_dir);
            List<FTPFile> files = this.ftp.listFiles();
            this.filter_ftpFiles(files);
            if (files.size() < 1) {
                return false;
            }

            Iterator iterator = files.iterator();

            while(iterator.hasNext()) {
                FTPFile file = (FTPFile)iterator.next();
                if (file.isFile()) {
                    logger.info("file name is : " + file.getName());
                    this.ftp.downFile(file, root_dir);
                    this.last_process_filestime = file.getTimestamp().getTimeInMillis();
                }
            }
        } catch (Exception var9) {
            logger.error("downFtpFiles() - exception e=", var9);
            return false;
        } finally {
            this.ftp.close();
        }

        return true;
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
                long b = o2.getTimestamp().getTimeInMillis();
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

    private void handleFile(File file) {
        List spIdList = this.spInfoDAO.getSpId();

        try {
            List<ServiceCapacity> ors = ServiceCapacity.parseFile(file, spIdList);
            logger.info(ors.size());
            this.serviceCapacityDAO.saveServiceCapacity(ors);
            this.backupFile(file);
        } catch (Exception var5) {
            var5.printStackTrace();
            logger.error("handleFile(" + file + ") - Exception e=" + var5);
        }

    }

    private void backupFile(File file) {
        String bak_dir = PropertiesHelper.getString("ftp_spsrvcapinfo_local_backupdir");
        File bak_file = new File(bak_dir + File.separatorChar + file.getName() + ".bak");
        FilesHelper.backupFile(file, bak_file);
    }

    public static void main(String[] args) {
        (new SyncServiceCapacityHandleThread()).start();
    }

    public ServiceCapacityDAO getServiceCapacityDAO() {
        return this.serviceCapacityDAO;
    }

    public void setServiceCapacityDAO(ServiceCapacityDAO serviceCapacityDAO) {
        this.serviceCapacityDAO = serviceCapacityDAO;
    }

    private static class VacFileFilter implements FileFilter {
        static final SyncServiceCapacityHandleThread.VacFileFilter vff = new SyncServiceCapacityHandleThread.VacFileFilter();

        private VacFileFilter() {
        }

        public boolean accept(File file) {
            String name = file.getName();
            if (!name.startsWith("spsrvcapinfo") || !name.endsWith(".rreq") && !name.endsWith(".areq")) {
                SyncServiceCapacityHandleThread.logger.warn("handleFiles() - file name invalid, String name=" + name);
                return false;
            } else {
                return true;
            }
        }
    }
}
