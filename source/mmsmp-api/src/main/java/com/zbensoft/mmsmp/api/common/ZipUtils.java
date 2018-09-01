package com.zbensoft.mmsmp.api.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 处理WAR文件工具类。可压缩或解压缩WAR文件。
 * 
 * @author 
 */
public class ZipUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

	static final int BUFFER = 8192;//缓冲


    /**
     * 压缩方法入口
     * @param srcPathName 源文件或路径
     * @param destFilePath 目标文件
     */
    public static void compress(String srcPathName,String destFilePath) {
        File file = new File(srcPathName);
        FileOutputStream fileOutputStream =null;
        CheckedOutputStream cos =null;
        ZipOutputStream out=null;
        if (!file.exists()){
            throw new RuntimeException(srcPathName + "not exist!");
        }
        try {
        	File zipFile= new File(destFilePath);
            fileOutputStream = new FileOutputStream(zipFile);
            cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            compress(file, out, basedir);
           
        } catch (Exception e) {
        	log.error("", e);
        }finally {
        	 try {
        		 if(out!=null){
	        		out.finish();
					out.close();
        		 }
        		 if(cos!=null){
        			 cos.flush();
        			 out.close();
        		 }
        		 if(fileOutputStream!=null){
        			 fileOutputStream.flush();
        			 fileOutputStream.close();
        		 }
			} catch (IOException e) {
			}
		}
    }
    
    /**
     * 压缩方法入口
     * @param srcPathName 源文件或路径
     * @param destFilePath 目标文件
     */
    public static void compress(String[] srcPathNames,String destFilePath) {
    	List<File> fileList=new ArrayList<>();
    	if(srcPathNames!=null&&srcPathNames.length>0){
    		for (int i = 0; i < srcPathNames.length; i++) {
    			 File file = new File(srcPathNames[i]);
    			 if (!file.exists()){
    		            continue;
    		        }
    			 fileList.add(file);
			}
    	}
    	if(fileList==null||fileList.size()==0){
    		throw new RuntimeException("files are not exist!");
    	}
       
        FileOutputStream fileOutputStream =null;
        CheckedOutputStream cos =null;
        ZipOutputStream out=null;
       
        try {
        	File zipFile= new File(destFilePath);
            fileOutputStream = new FileOutputStream(zipFile);
            cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            compress(fileList, out, basedir);
           
        } catch (Exception e) {
        	log.error("", e);
        }finally {
        	 try {
        		 if(out!=null){
	        		out.finish();
					out.close();
        		 }
        		 if(cos!=null){
        			 cos.flush();
        			 out.close();
        		 }
        		 if(fileOutputStream!=null){
        			 fileOutputStream.flush();
        			 fileOutputStream.close();
        		 }
			} catch (IOException e) {
			}
		}
    }
    
    
    /**
     * 压缩方法
     * @param file
     * @param out
     * @param basedir
     */
    private static void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            compressDirectory(file, out, basedir);
        } else {
            compressFile(file, out, basedir);
        }
    }
    /**
     * 压缩多文件方法
     * @param file
     * @param out
     * @param basedir
     */
    private static void compress(List<File> fileList, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
    	
    	if(fileList!=null&&fileList.size()>0){
    		for (File eachFile : fileList) {
    			 if (eachFile.isDirectory()) {
    		            compressDirectory(eachFile, out, basedir);
    		        } else {
    		            compressFile(eachFile, out, basedir);
    		        }
			}
    	}
       
    }
    

    /**
     * 压缩目录
     * @param dir
     * @param out
     * @param basedir
     */
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /**
     * 压缩文件
     * @param file
     * @param out
     * @param basedir
     */
    private static void compressFile(File file, ZipOutputStream out, String basedir) {
    	BufferedInputStream bis=null;
        if (!file.exists()) {
            return;
        }
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
          
        } catch (Exception e) {
        	log.error("", e);
        }finally {
        	  try {
        		  if(bis!=null){
        			  bis.close();
        		  }
			} catch (IOException e) {
			}
		}
    }
    
    public static void main(String[] args) {
    	String[] tmp={"C:\\Users\\mrcra\\Desktop\\989110290.txt","C:\\Users\\mrcra\\Desktop\\Distribucion_20180209.txt"};
    	ZipUtils.compress(tmp,"C:\\Users\\mrcra\\Desktop\\test.zip");
	}
}