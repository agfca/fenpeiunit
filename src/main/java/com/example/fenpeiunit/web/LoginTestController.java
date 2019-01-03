package com.example.fenpeiunit.web;

import com.example.fenpeiunit.cons.Cons;
import com.example.fenpeiunit.cons.GlobalVar;
import com.example.fenpeiunit.util.FileUtil;
import com.example.fenpeiunit.util.Image2StrUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class LoginTestController {

    private static Logger LOGGER = LoggerFactory.getLogger(EditDataController.class);

    public static void main(String[] args) {

        LOGGER.info("2222222");
    }

    public static void main1(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("result","false");

        String username = "cqjdhkj";
        String password = "oy123456";

        try(WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER)){
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setDownloadImages(false);
            webClient.getOptions().setAppletEnabled(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setUseInsecureSSL(true);//忽略ssl认证
            webClient.getOptions().setRedirectEnabled(false);
            webClient.getOptions().setTimeout(60000);
            //添加cookie
            //webClient.getCookieManager().addCookie(new Cookie("","",""));


            URL url = new URL(Cons.webUrl);
            HtmlPage htmlPage = webClient.getPage(url);
            //Cookie
            Set<Cookie> cookies = webClient.getCookies(url);
            String cookieValue = "";
            for (Cookie cookie:cookies){
                if(Cons.cookieKey.equals(cookie.getName())){
                    cookieValue = cookie.getValue();
                }
            }
            if("".equals(cookieValue)){
                throw new Exception("获取："+Cons.cookieKey+"为空");
            }
            //密码
            HtmlInput passwordElement =	htmlPage.getHtmlElementById(Cons.passwordInput);
            HtmlInput usernameElement =	htmlPage.getHtmlElementById(Cons.usernameInput);
            HtmlInput codeElement =	htmlPage.getHtmlElementById(Cons.codeInput);

            //submit 框
            HtmlSubmitInput submitButton = htmlPage.getHtmlElementById(Cons.submitInput);
            if(passwordElement==null){
                map.put("errMsg","通过ID获取 密码 框："+Cons.passwordInput+"错误");
            }
            if(usernameElement==null){
                map.put("errMsg","通过ID获取 用户 框："+Cons.usernameInput+"  错误");
            }
            if(codeElement==null){
                map.put("errMsg","通过ID获取 验证码 框："+Cons.codeInput+"  错误");
            }
            if(submitButton==null){
                map.put("errMsg","通过ID获取 submit 框："+Cons.submitInput+"  错误");
            }
            usernameElement.setAttribute("value",username);
            passwordElement.setAttribute("value",password);

            //保存验证码图片
            FileUtil.saveToFile(Cons.codeImageUrl,cookieValue);

            String code = Image2StrUtil.codeOCR();
            codeElement.setAttribute("value",code);

            HtmlPage nextPage = submitButton.click();

            DomElement w = nextPage.getElementById(Cons.welcomeInput);
            DomElement re = nextPage.getElementById(Cons.passwordInput);
            System.err.println(w.asText());
            System.err.println(nextPage.asText());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
