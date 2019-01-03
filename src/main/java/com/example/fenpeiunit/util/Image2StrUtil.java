package com.example.fenpeiunit.util;

import com.example.fenpeiunit.cons.Cons;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
public class Image2StrUtil {
    /**
     * 验证码识别
     */
    /*public static String codeOCR() throws Exception {
        //saveToFile("http://www.95504.net"+src+"?"+datetime);
        String strUrl = Cons.image2StrUrl+Cons.imagePath;
        URL url1 = new URL(strUrl);
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
        HttpGet getMethod3 = new HttpGet(uri);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse httpResponse3 = client.execute(getMethod3);
        String code = printResponse(httpResponse3);
        return code;
    }*/
    public static String codeOCR() throws Exception {
        String code = HttpUtils.sendGet(Cons.image2StrUrl,"",
                "url="+Cons.imagePath
        );
        return code;
    }

    public static String printResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        String responseString ="";
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();

        // 判断响应实体是否为空
        if (entity != null) {
            responseString = EntityUtils.toString(entity);
        }
        return responseString;
    }
}
