package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import com.zbensoft.mmsmp.ownbiz.ra.own.job.impl.DataCacheClearJob;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileSize {
    public static final Logger logger = Logger.getLogger(DataCacheClearJob.class);

    public FileSize() {
    }

    public static long fileSize(String filePath) {
        FileChannel fc = null;
        long filesize = 0L;

        try {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                FileInputStream fis = new FileInputStream(f);
                fc = fis.getChannel();
                filesize = fc.size();
            } else {
                logger.info("file doesn't exist or is not a file");
            }
        } catch (FileNotFoundException var16) {
            logger.error(var16);
        } catch (IOException var17) {
            logger.error(var17);
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException var15) {
                    logger.error(var15);
                }
            }

        }

        return filesize;
    }

    public static void main(String[] args) {
        System.out.println(fileSize("D:\\datasms\\content\\91597\\9159720140307154116838.eml"));
    }
}
