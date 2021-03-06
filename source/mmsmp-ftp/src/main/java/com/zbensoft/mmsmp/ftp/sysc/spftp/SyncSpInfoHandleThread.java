package com.zbensoft.mmsmp.ftp.sysc.spftp;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.zbensoft.mmsmp.ftp.util.FilesHelper;
import com.zbensoft.mmsmp.ftp.util.MyFtpTools;
import com.zbensoft.mmsmp.ftp.util.PropertiesHelper;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class SyncSpInfoHandleThread extends Thread {
    private static final Logger logger = Logger.getLogger(SyncSpInfoHandleThread.class);
    private static SyncSpInfoHandleThread _instance;
    private MyFtpTools ftp;
    private SpInfoDAO spInfoDAO = new SpInfoDAO();
    long last_process_filestime = System.currentTimeMillis() - (long)(PropertiesHelper.getInt("last_process_filestime_spInfo") * 24 * 3600) * 1000L;

    public SyncSpInfoHandleThread() {
        String ftp_server_ip = PropertiesHelper.getString("ftp_server_ip");
        int ftp_server_port = PropertiesHelper.getInt("ftp_server_port", 21);
        String ftp_login_username = PropertiesHelper.getString("ftp_login_username");
        String ftp_login_password = PropertiesHelper.getString("ftp_login_password");
        String ftp_server_dir = PropertiesHelper.getString("ftp_sp_server_dir");
        this.ftp = new MyFtpTools(ftp_server_ip, ftp_server_port, ftp_login_username, ftp_login_password, ftp_server_dir);
    }

    public static synchronized void startup() {
        if (_instance == null) {
            _instance = new SyncSpInfoHandleThread();
        }

        _instance.start();
        logger.info("SyncSpInfoHandleThread start...");
    }

    public void run() {
        while(true) {
            long ftp_monitor_interval = (long)PropertiesHelper.getInt("ftp_monitor_interval_spInfo");
            boolean ftp_monitor_thread_status = PropertiesHelper.getBoolean("ftp_sp_isrunning");
            if (ftp_monitor_thread_status && this.downFtpFiles(PropertiesHelper.getString("ftp_sp_server_dir"))) {
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
        File files = new File(PropertiesHelper.getString("ftp_sp_local_dir"));
        File[] listFiles = files.listFiles(SyncSpInfoHandleThread.VacFileFilter.vff);
        List<File> list_files = Arrays.asList(listFiles);
        Collections.sort(list_files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return o1.lastModified() < o2.lastModified() ? -1 : 1;
            }
        });
        return list_files;
    }

    private boolean downFtpFiles(String ftp_sp_server_dir) {
        String root_dir = PropertiesHelper.getString("ftp_sp_local_dir");

        try {
            this.ftp.connect(ftp_sp_server_dir);
            List<FTPFile> files = this.ftp.listFiles();
            this.filter_ftpFiles(files);
            if (files.size() < 1) {
                return false;
            }

            Iterator iterator = files.iterator();

            while(iterator.hasNext()) {
                FTPFile file = (FTPFile)iterator.next();
                if (file.isFile()) {
                    logger.info("ftp file name is : " + file.getName());
                    this.ftp.downFile(file, root_dir);
                    this.last_process_filestime = file.getTimestamp().getTimeInMillis();
                }
            }

            return true;
        } catch (Exception var9) {
            logger.error("downFtpFiles() - exception e=", var9);
        } finally {
            this.ftp.close();
        }

        return false;
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
            List<SpInfo> ors = SpInfo.parseFile(file, spIdList);
            logger.info(ors.size());
            this.spInfoDAO.saveSpInfo(ors);
            this.backupFile(file);
        } catch (Exception var5) {
            var5.printStackTrace();
            logger.error("handleFile(" + file + ") - Exception e=" + var5);
        }

    }

    private void backupFile(File file) {
        String bak_dir = PropertiesHelper.getString("ftp_sp_local_backupdir");
        File bak_file = new File(bak_dir + File.separatorChar + file.getName() + ".bak");
        FilesHelper.backupFile(file, bak_file);
    }

    public static void main(String[] args) {
        (new SyncSpInfoHandleThread()).start();
    }

    public SpInfoDAO getSpInfoDAO() {
        return this.spInfoDAO;
    }

    public void setSpInfoDAO(SpInfoDAO spInfoDAO) {
        this.spInfoDAO = spInfoDAO;
    }

    private static class VacFileFilter implements FileFilter {
        static final SyncSpInfoHandleThread.VacFileFilter vff = new SyncSpInfoHandleThread.VacFileFilter();

        private VacFileFilter() {
        }

        public boolean accept(File file) {
            String name = file.getName();
            if (name.startsWith("spinfo") && name.endsWith(".rreq")) {
                return true;
            } else {
                return name.startsWith("spinfo") && name.endsWith(".areq");
            }
        }
    }
}
