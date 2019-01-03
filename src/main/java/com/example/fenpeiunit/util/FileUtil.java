package com.example.fenpeiunit.util;

import com.example.fenpeiunit.cons.Cons;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {
    // 爬取网络的图片到本地
    public static void saveToFile(String destUrl, String cookieValue) {

        System.out.println(Cons.imagePath);
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestProperty("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
            httpUrl.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            httpUrl.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpUrl.setRequestProperty("Connection", "keep-alive");

            String cookie = Cons.cookieKey+"="+cookieValue;
            httpUrl.setRequestProperty("Cookie", cookie);

            httpUrl.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");

            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());

            File file = new File(Cons.imagePath);

            fos = new FileOutputStream(file);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("ClassCastException");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
