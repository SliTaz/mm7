package com.zbensoft.mmsmp.ftp.util;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class MyFtpTools {
    private static final Logger logger = Logger.getLogger(MyFtpTools.class);
    private FTPClient ftp = new FTPClient();
    private String ftp_server_ip = PropertiesHelper.getString("ftp_server_ip");
    private int ftp_server_port = PropertiesHelper.getInt("ftp_server_port", 21);
    private String ftp_login_username = PropertiesHelper.getString("ftp_login_username");
    private String ftp_login_password = PropertiesHelper.getString("ftp_login_password");
    private String ftp_server_dir;
    private boolean passive = true;

    public void setActiveMode() {
        this.passive = false;
    }

    public void setPassiveMode() {
        this.passive = true;
    }

    public MyFtpTools() {
    }

    public MyFtpTools(String ftp_ip, int ftp_port, String ftp_user, String ftp_pass, String ftp_dir) {
        this.ftp_server_ip = ftp_ip;
        this.ftp_server_port = ftp_port;
        this.ftp_login_username = ftp_user;
        this.ftp_login_password = ftp_pass;
        this.ftp_server_dir = ftp_dir;
        this.ftp.addProtocolCommandListener(new ProtocolCommandListener() {
            public void protocolCommandSent(ProtocolCommandEvent event) {
                if (PropertiesHelper.get_ftp_debug()) {
                    MyFtpTools.logger.info(event.getMessage());
                }

            }

            public void protocolReplyReceived(ProtocolCommandEvent event) {
                if (PropertiesHelper.get_ftp_debug()) {
                    MyFtpTools.logger.info(event.getMessage());
                }

            }
        });
    }

    public void connect(String directory) throws Exception {
        try {
            this.ftp.connect(this.ftp_server_ip, this.ftp_server_port);
            this.ftp.login(this.ftp_login_username, this.ftp_login_password);
            this.ftp.setFileType(2);
            this.ftp.setControlEncoding("UTF-8");
            if (this.passive) {
                this.ftp.enterLocalPassiveMode();
            }

            if (this.ftp.changeWorkingDirectory(directory)) {
                logger.info("changed into: " + directory);
            } else {
                logger.error("change directory fail");
            }
        } catch (RuntimeException var3) {
            var3.printStackTrace();
        }

    }

    public void downFile(FTPFile ftpfile, String filePath) throws Exception {
        String newfile = filePath + ftpfile.getName();
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileOutputStream os = new FileOutputStream(newfile);
        this.ftp.retrieveFile(ftpfile.getName(), os);
        os.close();
        (new File(newfile)).setLastModified(ftpfile.getTimestamp().getTimeInMillis());
    }

    public List<FTPFile> listFiles() throws Exception {
        FTPFile[] ffs = this.ftp.listFiles();
        List list = Arrays.asList(ffs);
        List arrayList = new ArrayList(list);
        return arrayList;
    }

    public void deleteFile(FTPFile ftpfile) throws Exception {
        this.ftp.deleteFile(ftpfile.getName());
    }

    public void test() throws Exception {
        FTPFile[] ffs = this.ftp.listFiles();
        List<FTPFile> files = Arrays.asList(ffs);
        Collections.sort(files, new Comparator<FTPFile>() {
            public int compare(FTPFile o1, FTPFile o2) {
                Date time1 = o1.getTimestamp().getTime();
                Date time2 = o2.getTimestamp().getTime();
                return time1.compareTo(time2);
            }
        });

        for (int i = 0; i < files.size(); ++i) {
            System.out.println(files.get(i));
        }

    }

    public void close() {
        try {
            this.ftp.logout();
            if (this.ftp.isConnected()) {
                this.ftp.disconnect();
            }
        } catch (Exception var2) {
            ;
        }

    }

    public static void main(String[] args) throws Exception {
        MyFtpTools ftp = new MyFtpTools();
        ftp.connect(args.length == 0 ? "/" : args[0]);
        ftp.test();
        ftp.close();
    }
}
