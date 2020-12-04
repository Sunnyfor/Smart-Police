package com.zhkj.smartpolice.utils.input;

import com.sunny.zy.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final int BUFFER_SIZE = 8192;

    public static boolean fileIsExist(String filePath) {
        if (filePath == null || filePath.length() < 1) {
            return false;
        }

        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    public static InputStream readFile(String filePath) {
        InputStream is = null;
        if (fileIsExist(filePath)) {
            File f = new File(filePath);
            try {
                is = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return is;
    }

    public static byte[] readBytes(String filePath) {
        InputStream inputstream = readFile(filePath);
        if (inputstream == null) {
            return null;
        }
        BufferedInputStream in = new BufferedInputStream(inputstream);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        byte[] data = null;
        try {
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            data = outStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
                in = null;
            } catch (IOException e) {
            }
            try {
                outStream.close();
                outStream = null;
            } catch (IOException e) {
            }
        }
        // 把outStream里的数据写入内存
        return data;
    }

    public static boolean writeByteFile(String filePath, byte[] bytes) {
        boolean success = true;
        File distFile = new File(filePath);
        if (!distFile.getParentFile().exists()) {
            try {
                distFile.getParentFile().mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath), BUFFER_SIZE);
            bos.write(bytes);
        } catch (Exception e) {
            LogUtil.INSTANCE.e("save " + filePath + " failed!");
            success = false;
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (IOException e) {
                }
            }

        }

        return success;
    }
}