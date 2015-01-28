package com.baitaner.common.utils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zliu on 14/12/13.
 */
public class FileUtils {
    public static void zip(String zipFileName, List<File> inputFiles) throws Exception {
//        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
//                zipFileName));
//        BufferedOutputStream bo = new BufferedOutputStream(out);
//        zip(out, inputFiles, bo);
//        bo.close();
//        out.close(); // 输出流关闭
        fileToZip(inputFiles,zipFileName);
    }

    private  static void zip(ZipOutputStream out, List<File> fs,
                             BufferedOutputStream bo) throws Exception { // 方法重载

        for(File baseFile:fs){
            out.putNextEntry(new ZipEntry(baseFile.getName())); // 创建zip压缩进入点base
            FileInputStream in = new FileInputStream(baseFile);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }

    /**
      * 将存放在sourceFilePath目录下的源文件,打包成fileName名称的ZIP文件,并存放到zipFilePath。
      * @param sourceFilePath 待压缩的文件路径
      * @param zipFilePath    压缩后存放路径
      * @param fileName   压缩后文件的名称
      * @return flag
      */
    public static boolean fileToZip(List<File> sourceFiles,String dstFile) {
        boolean flag = false;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;


        try {
            File zipFile = new File(dstFile);
            if(zipFile.exists()) {
                System.out.println(">>>>>> " + dstFile + " 目录下存在名字为：" + dstFile + ".zip" + " 打包文件. <<<<<<");
                zipFile.delete();
            }

            if(null == sourceFiles || sourceFiles.size() < 1) {
                System.out.println(">>>>>> 待压缩的文件目录：" + sourceFiles + " 里面不存在文件,无需压缩. <<<<<<");
            } else {
                fos = new FileOutputStream(zipFile);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                byte[] bufs = new byte[1024*10];
                for(File file:sourceFiles) {
                    // 创建ZIP实体,并添加进压缩包
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);
                    // 读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis,1024*10);
                    int read = 0;
                    while((read=bis.read(bufs, 0, 1024*10)) != -1) {
                     zos.write(bufs, 0, read);
                    }
                }
                flag = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
             e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
        // 关闭流
        try {
            if(null != bis) bis.close();
            if(null != zos) zos.close();
            } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            }
        }
        return flag;
    }
}
