package com.example.fenpeiunit.util;

import com.example.fenpeiunit.cons.Cons;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {

    /**
     * 向指定URL发送GET方法的请求
     * @param url
     *                      发送请求的URL
     * @param param
     *                      请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String cookie, String... param) {
        String result = "";
        BufferedReader in = null;
        try {
            StringBuffer urlNameString = new StringBuffer();
            urlNameString.append(url+"?1=1");

            for (String temp : param){
                urlNameString.append("&"+temp);
            }
            urlNameString.append("&date="+ Math.random()+"&date1"+ Math.random());// + "?" + param;

            URL realUrl = new URL(urlNameString.toString());
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
            /*
            connection.setRequestMethod("GET");*/
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept-Charset","utf-8");
            //cookie
            connection.setRequestProperty("Cookie", cookie);

            /* setConnectTimeout：设置连接主机超时（单位：毫秒）
            setReadTimeout：设置从主机读取数据超时（单位：毫秒）*/
            connection.setConnectTimeout(Cons.connectTimeout);
            connection.setReadTimeout(Cons.readTimeout);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
            }*/
            /*StringBuffer stringBuffer = new StringBuffer();
            GZIPInputStream gZIPInputStream = null;
            String encoding = connection.getContentEncoding();
            if("gzip".equals(encoding)){
                gZIPInputStream = new GZIPInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gZIPInputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //转化为UTF-8的编码格式
                    //line = new String(line.getBytes("UTF-8"));
                    stringBuffer.append(line);
                }
                bufferedReader.close();
            }else{
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //转化为UTF-8的编码格式
                    //line = new String(line.getBytes("UTF-8"));
                    stringBuffer.append(line);
                }
                bufferedReader.close();
            }*/
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line=in.readLine())!=null){
                stringBuffer.append(line);
            }
            result = stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }finally {// 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String cookie, String... param) {
        String result = "";
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            StringBuffer params = new StringBuffer("1=1");
            for (String temp : param){
                params.append("&"+temp);
            }
            params.append("&date="+ Math.random()+"&date1"+ Math.random());// + "?" + param;

            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
            //cookie
            conn.setRequestProperty("encoding", "UTF-8");
            conn.setRequestProperty("Cookie", cookie);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            /* setConnectTimeout：设置连接主机超时（单位：毫秒）
            setReadTimeout：设置从主机读取数据超时（单位：毫秒）*/
            conn.setConnectTimeout(Cons.connectTimeout);
            conn.setReadTimeout(Cons.readTimeout);

            // 创建一个输出缓冲区对象,将要输出的字符流写出对象传入
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(conn.getOutputStream()));
            bufferedWriter.write(params.toString());
            // 刷新输出缓冲区
            bufferedWriter.flush();

            // 将输入字节流对象包装成输入字符流对象，并将字符编码为UTF-8格式
            // 创建一个输入缓冲区对象，将要输入的字符流对象传入
            bufferedReader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            result = stringBuffer.toString();

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String doPOST(String urlAddress, String params) {
        try {
            // 创建URL对象
            URL url = new URL(urlAddress);
            // 打开连接 获取连接对象
            URLConnection connection = url.openConnection();
            // 设置请求编码
            connection.addRequestProperty("encoding", "UTF-8");
            // 设置允许输入
            connection.setDoInput(true);
            // 设置允许输出
            connection.setDoOutput(true);

            // 从连接对象中获取输出字节流对象
            OutputStream outputStream = connection.getOutputStream();
            // 将输出的字节流对象包装成字符流写出对象
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // 创建一个输出缓冲区对象,将要输出的字符流写出对象传入
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            // 向输出缓冲区中写入请求参数
            bufferedWriter.write(params);
            // 刷新输出缓冲区
            bufferedWriter.flush();

            // 从连接对象中获取输入字节流对象
            InputStream inputStream = connection.getInputStream();
            // 将输入字节流对象包装成输入字符流对象，并将字符编码为UTF-8格式
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // 创建一个输入缓冲区对象，将要输入的字符流对象传入
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 创建一个字符串对象，用来接收每次从输入缓冲区中读入的字符串
            String line;
            // 创建一个可变字符串对象，用来装载缓冲区对象的最终数据，使用字符串追加的方式，将响应的所有数据都保存在该对象中
            StringBuilder stringBuilder = new StringBuilder();
            // 使用循环逐行读取缓冲区的数据，每次循环读入一行字符串数据赋值给line字符串变量，直到读取的行为空时标识内容读取结束循环
            while ((line = bufferedReader.readLine()) != null) {
                // 将缓冲区读取到的数据追加到可变字符对象中
                stringBuilder.append(line);
            }
            // 依次关闭打开的输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            // 依次关闭打开的输出流
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
            // 将可变字符串转换成String对象返回
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
