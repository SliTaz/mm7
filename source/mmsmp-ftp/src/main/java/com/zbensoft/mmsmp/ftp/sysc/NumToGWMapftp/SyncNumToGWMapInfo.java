package com.zbensoft.mmsmp.ftp.sysc.NumToGWMapftp;

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

public class SyncNumToGWMapInfo {
    private static final Logger logger = Logger.getLogger(SyncNumToGWMapInfo.class);
    private MyFtpTools ftp;
    private NumToGWMapInfoDAO numToGWMapInfoDAO;
    long last_process_filestime = System.currentTimeMillis() - (long)(PropertiesHelper.getInt("last_process_filestime_numToGWMapinfo") * 24 * 3600) * 1000L;

    public SyncNumToGWMapInfo() {
        String ftp_server_ip = PropertiesHelper.getString("ftp_server_ip");
        int ftp_server_port = PropertiesHelper.getInt("ftp_server_port", 21);
        String ftp_login_username = PropertiesHelper.getString("ftp_login_username");
        String ftp_login_password = PropertiesHelper.getString("ftp_login_password");
        String ftp_server_dir = PropertiesHelper.getString("ftp_numtogwmapinfo_server_dir");
        this.ftp = new MyFtpTools(ftp_server_ip, ftp_server_port, ftp_login_username, ftp_login_password, ftp_server_dir);
    }

    public void run() {
        boolean ftp_monitor_thread_status = PropertiesHelper.getBoolean("ftp_numtogwmapinfo_isrunning");
        logger.info("=======run=======ftp_monitor_thread_status==" + ftp_monitor_thread_status);
        if (ftp_monitor_thread_status && this.downFtpFiles(PropertiesHelper.getString("ftp_numtogwmapinfo_server_dir"))) {
            logger.info("handleFiles()");
            this.handleFiles();
        }

    }

    private void handleFiles() {
        List<File> list_files = this.getFileList();
        Iterator iterator = list_files.iterator();

        while(iterator.hasNext()) {
            File file = (File)iterator.next();
            logger.info("handleFiles() - File file=" + file);
            this.handleFile(file);
        }

    }

    private List<File> getFileList() {
        File files = new File(PropertiesHelper.getString("ftp_numtogwmapinfo_local_dir"));
        File[] listFiles = files.listFiles(SyncNumToGWMapInfo.VacFileFilter.vff);
        List<File> list_files = Arrays.asList(listFiles);
        Collections.sort(list_files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return o1.lastModified() < o2.lastModified() ? -1 : 1;
            }
        });
        return list_files;
    }

    private boolean downFtpFiles(String ftp_sp_server_dir) {
        String root_dir = PropertiesHelper.getString("ftp_numtogwmapinfo_local_dir");

        try {
            logger.info("starting to connect ftp server and change to dir : " + ftp_sp_server_dir);
            this.ftp.connect(ftp_sp_server_dir);
            List<FTPFile> files = this.ftp.listFiles();
            this.filter_ftpFiles(files);
            if (files.size() < 1) {
                return false;
            }

            int i = 0;
            Iterator iterator = files.iterator();

            while(iterator.hasNext()) {
                FTPFile file = (FTPFile)iterator.next();
                ++i;
                if (file.isFile() && i == files.size()) {
                    logger.info("file name is : " + file.getName());
                    this.ftp.downFile(file, root_dir);
                    this.last_process_filestime = file.getTimestamp().getTimeInMillis();
                }
            }
        } catch (Exception var10) {
            logger.error("downFtpFiles() - exception e=", var10);
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
                long b = o1.getTimestamp().getTimeInMillis();
                return (int)(a - b >> 0);
            }
        });
    }

    private void handleFile(File file) {
        try {
            List<NumToGWMapInfo> ors = NumToGWMapInfo.parseFile(file);
            this.numToGWMapInfoDAO.saveNumToGWMapInfo(ors);
            this.backupFile(file);
        } catch (Exception var4) {
            var4.printStackTrace();
            logger.error("handleFile(" + file + ") - Exception e=" + var4);
        }

    }

    private void backupFile(File file) {
        String bak_dir = PropertiesHelper.getString("ftp_numtogwmapinfo_local_backupdir");
        File bak_file = new File(bak_dir + File.separatorChar + file.getName() + ".bak");
        FilesHelper.backupFile(file, bak_file);
    }

    public static void main(String[] args) {
        (new SyncNumToGWMapInfo()).run();
    }

    public NumToGWMapInfoDAO getNumToGWMapInfoDAO() {
        return this.numToGWMapInfoDAO;
    }

    public void setNumToGWMapInfoDAO(NumToGWMapInfoDAO numToGWMapInfoDAO) {
        this.numToGWMapInfoDAO = numToGWMapInfoDAO;
    }

    private static class VacFileFilter implements FileFilter {
        static final SyncNumToGWMapInfo.VacFileFilter vff = new SyncNumToGWMapInfo.VacFileFilter();

        private VacFileFilter() {
        }

        public boolean accept(File file) {
            String name = file.getName();
            return name.startsWith("JSJ") && name.endsWith(".TXT");
        }
    }
}
