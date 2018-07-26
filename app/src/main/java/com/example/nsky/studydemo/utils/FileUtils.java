package com.example.nsky.studydemo.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by NSKY on 2018/6/30.
 */

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 复制文件（非目录）
     *
     * @param srcFile  要复制的源文件
     * @param destFile 复制到的目标文件
     * @return
     */
    private static boolean copyFile(File srcFile, File destFile) {
        try {

            long startTime = System.currentTimeMillis();
            Log.e(TAG, "copyFile start time :" + startTime);
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            long endTime = System.currentTimeMillis();
            Log.e(TAG, "copyFile end time :" + endTime + "  total time: " + (endTime - startTime));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 移动文件目录到某一路径下
     *
     * @return
     */
    public static boolean moveFile(File srcDir, File destDir, String fileName) {
        try {
            if (!srcDir.exists()) {
                Log.e(TAG, "===src path not exist===");
                return false;
            }
            if (!destDir.exists()) {
                Log.e(TAG, "moveFile dir not exist   mkdirs");
                destDir.mkdirs();
            }
            long startTime = System.currentTimeMillis();
            Log.e(TAG, "moveFile start time :" + startTime);
            //复制后删除原目录
            File srcPath = new File(srcDir, fileName);
            File desPath = new File(destDir, fileName);
            if (copyFile(srcPath, desPath)) {
                deleteFile(srcPath);
                long endTime = System.currentTimeMillis();
                Log.e(TAG, "moveFile end time :" + endTime + "  total time: " + (endTime - startTime));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件（包括目录）
     *
     * @param delFile
     */
    public static void deleteFile(File delFile) {
        //如果是目录递归删除
        long startTime = System.currentTimeMillis();
        Log.e(TAG, "deleteFile start time :" + startTime);
        if (delFile.isDirectory()) {
            File[] files = delFile.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        } else {
            delFile.delete();
        }
        //如果不执行下面这句，目录下所有文件都删除了，但是还剩下子目录空文件夹
        delFile.delete();
        long endTime = System.currentTimeMillis();
        Log.e(TAG, "deleteFile end time :" + endTime + "  total time: " + (endTime - startTime));
    }
}
