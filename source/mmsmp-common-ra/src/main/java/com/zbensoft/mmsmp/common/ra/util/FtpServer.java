 package com.zbensoft.mmsmp.common.ra.util;
 
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.net.SocketException;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import org.apache.commons.net.ftp.FTPClient;
 import org.apache.commons.net.ftp.FTPFile;
 
 public class FtpServer
   implements FtpServerInterface
 {
   private FTPClient ftp;
   private String host;
   private int port;
 
   public FtpServer(String host)
   {
     this.ftp = new FTPClient();
     this.ftp.setControlEncoding("GBK");
     this.host = host;
     this.port = 21;
   }
 
   public FtpServer(String host, int port)
   {
     this.ftp = new FTPClient();
     this.ftp.setControlEncoding("GBK");
     this.host = host;
     this.port = port;
   }
 
   public void connect(String username, String psw)
     throws SocketException, IOException
   {
     this.ftp.connect(this.host, this.port);
 
     this.ftp.login(username, psw);
 
     this.ftp.enterLocalPassiveMode();
   }
 
   public void dir(String dirpath)
     throws IOException
   {
     FTPFile[] files = this.ftp.listFiles(dirpath);
     System.out.println("Directory is " + this.ftp.printWorkingDirectory());
     System.out.println("Number of files in dir: " + files.length);
     DateFormat fileDate = new SimpleDateFormat("yyyy-MM-dd hh:mm");
     for (int i = 0; i < files.length; i++)
     {
       System.out.print(files[i].getName());
       System.out.print(" \t\t ");
       System.out.println(fileDate.format(files[i].getTimestamp().getTime()));
     }
   }
 
   public void changeDir(String path)
     throws IOException
   {
     this.ftp.changeWorkingDirectory(path);
   }
 
   public void changeToParentDir() throws IOException {
     this.ftp.changeToParentDirectory();
   }
 
   public void makedir(String pathname) throws IOException
   {
     this.ftp.makeDirectory(pathname);
   }
 
   public void download(String destpath, String remotePath, String remotefilename, boolean rename, String localfilename)
     throws IOException, FileNotFoundException
   {
     String filename = remotefilename;
     if (rename)
     {
       filename = localfilename;
     }
 
     File file = new File(destpath + filename);
     FileOutputStream fos = new FileOutputStream(file);
     this.ftp.retrieveFile(remotePath + remotefilename, fos);
     fos.close();
   }
 
   public void downloadDirectory(String destDirs, String path)
     throws IOException
   {
     String dirName = FileProcessor.getFileName(path);
     File dir = new File(destDirs, dirName);
     dir.mkdir();
 
     String destDir = destDirs + "/" + dirName;
     retrieveDirectory(destDir, path);
   }
 
   public void retrieveDirectory(String destDirs, String path) throws IOException {
     FTPFile[] files = this.ftp.listFiles(path);
     for (int i = 0; i < files.length; i++)
     {
       FTPFile ftpf = files[i];
       if (ftpf.getName().startsWith("."))
       {
         continue;
       }
       if (ftpf.isDirectory())
       {
         File subd = new File(destDirs, ftpf.getName());
 
         subd.mkdirs();
         retrieveDirectory(subd.getAbsolutePath(), path + "/" + ftpf.getName());
       }
       else
       {
         FileOutputStream fous = new FileOutputStream(new File(destDirs, ftpf.getName()));
         this.ftp.retrieveFile(path + "/" + ftpf.getName(), fous);
         fous.close();
       }
     }
   }
 
   public boolean deleteFile(String path)
     throws IOException
   {
     boolean flag = this.ftp.deleteFile(path);
 
     return flag;
   }
 
   public void deleteDirectory(String path)
     throws IOException
   {
     FTPFile[] files = this.ftp.listFiles(path);
     for (int i = 0; i < files.length; i++)
     {
       FTPFile ftpf = files[i];
       if (ftpf.getName().startsWith("."))
       {
         continue;
       }
       if (ftpf.isDirectory())
       {
         deleteDirectory(path + "/" + ftpf.getName());
       }
       else
       {
         this.ftp.deleteFile(path + "/" + ftpf.getName());
       }
     }
     this.ftp.removeDirectory(path);
     System.out.println(this.ftp.getReplyString());
   }
 
   public void uploadFile(String destpath, InputStream in, String filename) throws IOException
   {
     this.ftp.setFileType(2);
     this.ftp.setControlEncoding("GBK");
 
     this.ftp.storeFile(destpath + filename, in);
   }
 
   public void move(String from, String to)
     throws IOException
   {
     this.ftp.rename(from, to);
   }
 
   public void logout()
     throws IOException
   {
     this.ftp.logout();
     this.ftp.disconnect();
   }
 
   public FTPFile[] searchFile(String dirpath)
     throws IOException
   {
     FTPFile[] files = this.ftp.listFiles(dirpath);
     return files;
   }
 
   public static FtpServer getInstance()
   {
     return null;
   }
 
   public FTPClient getFc()
   {
     return null;
   }
 
   public FTPClient getFtp() {
     return this.ftp;
   }
 
   public void setFtp(FTPClient ftp) {
     this.ftp = ftp;
   }
 }
