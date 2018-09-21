package com.zbensoft.mmsmp.ftp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zbensoft.mmsmp.ftp.service.impl.DownloadImpl;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class FtpUtil {
    private String serverIp;
    private int serverPort;
    private String username;
    private String password;
    private String serverDir;
    private String localDir;
    private int retry;
    private int currentCount;
    private FTPClient ftp;
    private static final Logger logger = Logger.getLogger(FtpUtil.class);

    public FtpUtil() {
    }

    public int getRetry() {
        return this.retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public void disconnect() throws Exception {
        if (this.ftp != null && this.ftp.isConnected()) {
            this.ftp.logout();
            this.ftp.disconnect();
        }

        logger.info("ftp is disconnect.");
    }

    public boolean connect() {
        this.retry = 3;
        boolean result = false;

        try {
            this.ftp = new FTPClient();
            this.ftp.setControlEncoding("UTF-8");
            this.ftp.enterLocalPassiveMode();
            this.ftp.connect(this.serverIp, this.serverPort);
            if (this.ftp.isConnected()) {
                this.ftp.setFileType(2);
                if (this.ftp.login(this.username, this.password)) {
                    logger.info("ftp login success.");
                    if (this.ftp.changeWorkingDirectory(this.serverDir)) {
                        logger.info("change working dir success:" + this.serverDir);
                        result = true;
                    } else {
                        logger.info("change working dir fail:" + this.serverDir);
                    }
                }
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return result;
    }

    public void downloadDir(String[] dirs, String regex) throws Exception {
        this.ftp.changeWorkingDirectory(this.serverDir);
        this.ftp.enterLocalPassiveMode();
        FTPFile[] ftpDirs = this.ftp.listDirectories();
        FTPFile[] var7 = ftpDirs;
        int var6 = ftpDirs.length;

        int var5;
        for(var5 = 0; var5 < var6; ++var5) {
            FTPFile file = var7[var5];
            logger.info("find dir " + file.getName());
        }

        String[] var14 = dirs;
        var6 = dirs.length;

        for(var5 = 0; var5 < var6; ++var5) {
            String dir = var14[var5];
            boolean exists = false;
            FTPFile[] var12 = ftpDirs;
            int var11 = ftpDirs.length;

            for(int var10 = 0; var10 < var11; ++var10) {
                FTPFile file = var12[var10];
                if (file.getName().trim().equals(dir)) {
                    this.downloadDirByShell(file, regex);
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                logger.error("remote dir " + dir + " is not exists.");
            }
        }

        logger.info("download file is finish.");
    }

    private void downloadDirByShell(FTPFile ftpDir, String regex) throws Exception {
        String localSPDir = this.localDir + ftpDir.getName();
        this.mkdirs(localSPDir);
        String shell = DownloadImpl.class.getResource("").toString().substring(5) + "downloadAddFile.sh";
        FileWriter out = new FileWriter(new File(shell));
        out.write("#!/bin/bash\n");
        out.write("ftp -n<<!\n");
        out.write("open " + this.serverIp + "\n");
        out.write("user " + this.username + " " + this.password + "\n");
        out.write("binary\n");
        out.write("cd " + this.serverDir + ftpDir.getName() + "\n");
        out.write("lcd " + this.localDir + ftpDir.getName() + "\n");
        out.write("prompt\n");
        out.write("mget *" + regex + "*.req" + "\n");
        out.write("close\n");
        out.write("bye\n");
        out.write("!");
        out.flush();
        out.close();
        Runtime run = Runtime.getRuntime();

        try {
            Process p = run.exec("chmod 777 " + shell);
            p.waitFor();
            this.printProcessInfo(p);
            p = run.exec(shell);
            p.waitFor();
            this.printProcessInfo(p);
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        File file = new File(localSPDir);
        File[] lf = file.listFiles();

        for(int i = 0; i < lf.length; ++i) {
            logger.info("Download the file name: " + lf[i].getName());
        }

        logger.info(ftpDir.getName() + "增量文件下载总数为：" + lf.length + " 个");
    }

    private void printProcessInfo(Process p) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuffer sb = new StringBuffer();

        String line;
        while((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        String result = sb.toString();
        logger.info(result);
    }

    private void downloadDir(FTPFile ftpDir, String regex) throws Exception {
        String localSPDir = this.localDir + ftpDir.getName();
        this.mkdirs(localSPDir);
        this.ftp.changeWorkingDirectory(ftpDir.getName());
        this.ftp.enterLocalPassiveMode();
        FTPFile[] ftpFiles = this.ftp.listFiles();
        FTPFile[] var8 = ftpFiles;
        int var7 = ftpFiles.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            FTPFile file = var8[var6];
            if (file.isFile()) {
                Pattern pat = Pattern.compile(regex);
                Matcher mat = pat.matcher(file.getName());
                if (mat.find()) {
                    ExecutorService exec = Executors.newFixedThreadPool(1);

                    try {
                        Future<String> future = exec.submit(new FtpUtil.DownloadTask(localSPDir, file));
                        String obj = (String)future.get(10000L, TimeUnit.MILLISECONDS);
                        logger.info("task is success return:" + obj);
                    } catch (TimeoutException var14) {
                        exec.shutdown();
                        logger.error("file " + file.getName() + "is break:" + var14);
                    } catch (Exception var15) {
                        exec.shutdown();
                        logger.error("operate fail file:" + file.getName() + ":" + var15);
                        var15.printStackTrace();
                    }
                }
            }
        }

    }

    private void downloadFile(FTPFile ftpFile, String localSPDir) throws Exception {
        String file = localSPDir + File.separator + ftpFile.getName();
        if (!(new File(file)).exists()) {
            FileOutputStream os = new FileOutputStream(file);
            this.ftp.enterLocalPassiveMode();
            this.ftp.retrieveFile(ftpFile.getName(), os);
            os.flush();
            os.close();
            (new File(file)).setLastModified(ftpFile.getTimestamp().getTimeInMillis());
            logger.info("download file " + file + " success.");
        } else {
            logger.info(file + " already exists.");
        }

    }

    private File mkdirs(String dirStr) {
        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerDir() {
        return this.serverDir;
    }

    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }

    public String getLocalDir() {
        return this.localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
        if (!localDir.endsWith(File.separator)) {
            this.localDir = this.localDir + File.separator;
        }

    }

    class DownloadTask implements Callable<String> {
        private String localSPDir;
        private FTPFile ftpFile;

        public DownloadTask(String localSPDir, FTPFile ftpFile) {
            this.localSPDir = localSPDir;
            this.ftpFile = ftpFile;
        }

        public String call() throws Exception {
            String file = this.localSPDir + File.separator + this.ftpFile.getName();
            if (!(new File(file)).exists()) {
                FileOutputStream os = new FileOutputStream(file);
                FtpUtil.this.ftp.enterLocalPassiveMode();
                FtpUtil.this.ftp.retrieveFile(this.ftpFile.getName(), os);
                os.flush();
                os.close();
                (new File(file)).setLastModified(this.ftpFile.getTimestamp().getTimeInMillis());
                FtpUtil.logger.info("download file " + file + " success.");
            } else {
                FtpUtil.logger.info(file + " already exists.");
            }

            return "thread run is finish.";
        }
    }
}
