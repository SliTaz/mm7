package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

import java.io.File;
import java.io.FilenameFilter;

public class SearchDir {
    private String name;
    private SearchDir.MyDealFile dealFile;
    private FilenameFilter filter;
    private static String separator = "";
    private static int depth = 0;
    private static long count;

    static {
        if (File.separator.equals("\\")) {
            separator = "\\\\";
        } else {
            separator = "/";
        }

    }

    public SearchDir(String name, SearchDir.MyDealFile dealFile, FilenameFilter filter) {
        this.name = name;
        this.dealFile = dealFile;
        this.filter = filter;
    }

    public void doit() throws Exception {
        this.readTree(new File(this.name));
    }

    private void readTree(File name) throws Exception {
        if (name.isFile()) {
            this.dealFile.doFile(name);
        } else if (name.isDirectory()) {
            File[] files = name.listFiles(this.filter);

            for(int i = 0; i < files.length; ++i) {
                this.readTree(files[i]);
            }

            this.dealFile.doFile(name);
        }

    }

    static void mkdir(String curr, int num) {
        if (curr.split(separator).length != depth) {
            if ((new File(curr)).mkdirs()) {
                ++count;
                System.out.println("Create Dir:" + curr);
            } else {
                System.out.println("Fail to create Dir:" + curr + count);
                System.exit(0);
            }

            for(int i = 0; i < num; ++i) {
                String dir = curr + File.separator + i;
                mkdir(dir, num);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        String Dir = "";
        String path;
        if (args != null && args.length >= 3) {
            path = args[0];
            depth = Integer.parseInt(args[1]) + path.split(separator).length + 1;
            int var5 = Integer.parseInt(args[2]);
        } else {
            path = "E:\\test";
            depth = 5;
            boolean var3 = true;
        }

        SearchDir dir = new SearchDir(path, new SearchDir.MyDealFile() {
            public void doFile(File file) {
                String attr = "";
                attr = file.isFile() ? "normal    file:" : "Directory file:";
                if (file.delete()) {
                    System.err.println("Delete " + attr + file.getAbsolutePath());
                }

            }
        }, new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File namePath = new File(dir.getAbsolutePath() + "\\" + name);
                return namePath.isDirectory() || name.toLowerCase().endsWith("");
            }
        });
        dir.doit();
    }

    public interface MyDealFile {
        void doFile(File var1);
    }
}

