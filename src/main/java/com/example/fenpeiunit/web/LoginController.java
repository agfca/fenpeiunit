package com.example.fenpeiunit.web;

import com.example.fenpeiunit.cons.Cons;
import com.example.fenpeiunit.cons.GlobalVar;
import com.example.fenpeiunit.util.FileUtil;
import com.example.fenpeiunit.util.Image2StrUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class LoginController {

    @Autowired
    private GlobalVar globalVar;
    @Autowired
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 获取登录cookie
     */
    @RequestMapping("/login")
    public Map<String,String> login(HttpSession session, @RequestParam String username, @RequestParam String password, @RequestParam String parentCardNum){
        Map<String,String> map = new HashMap<>();
        map.put("result","false");
        try(WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER)){//UserAgent
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setDownloadImages(false);
            webClient.getOptions().setAppletEnabled(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setTimeout(Cons.webTimeout);
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
                throw new Exception("获取cookie："+Cons.cookieKey+"为空");
            }
            //密码
            HtmlInput passwordElement =	htmlPage.getHtmlElementById(Cons.passwordInput);
            HtmlInput usernameElement =	htmlPage.getHtmlElementById(Cons.usernameInput);
            HtmlInput codeElement =	htmlPage.getHtmlElementById(Cons.codeInput);

            //submit 框
            HtmlSubmitInput submitButton = htmlPage.getHtmlElementById(Cons.submitInput);
            if(passwordElement==null){
                throw new Exception("通过ID获取 密码 框："+Cons.passwordInput+"错误");
            }
            if(usernameElement==null){
                throw new Exception("通过ID获取 用户 框："+Cons.usernameInput+"  错误");
            }
            if(codeElement==null){
                throw new Exception("通过ID获取 验证码 框："+Cons.codeInput+"  错误");
            }
            if(submitButton==null){
                throw new Exception("通过ID获取 submit 框："+Cons.submitInput+"  错误");
            }
            usernameElement.setAttribute("value",username);
            passwordElement.setAttribute("value",password);

            //保存验证码图片
            FileUtil.saveToFile(Cons.codeImageUrl,cookieValue);
            String code = Image2StrUtil.codeOCR();

            if(code==null||code.length()!=4){
                throw new Exception("验证码识别有误，识别为："+(code==null?"null":code));
            }
            codeElement.setAttribute("value",code);

            //提交 form
            HtmlPage nextLink = submitButton.click();

            DomElement result = nextLink.getElementById(Cons.passwordInput);

            if(result!=null){//有密码框   还在登录页面  获取提示信息
                String allText = nextLink.asXml();//AsText
                Pattern pattern = Pattern.compile(Cons.regTip);
                Matcher m = pattern.matcher(allText);
                String msg = "";
                if (m.find(1)) {
                    msg = m.group(1);
                }
                throw new Exception("登录失败，登录页提示：" + msg+"；验证码解析为："+code);
            }else{//登录成功
                DomElement temp0 = nextLink.getElementById(Cons.welcomeInput);
                String cookie = Cons.cookieKey+"="+cookieValue;
                map.put("cookie",cookie);
                if(temp0==null){//未找到登录后用户信息
                    throw new Exception("未获取到主页欢迎信息，登录失败");
                }else{
                    map.put("result","true");
                    map.put("msg","登录成功");
                    String asn = parentCardNum;//TODO  主卡asn  通过 请求获取
                    //添加  global var
                    globalVar.setVarMap(asn,username,password,cookie);
                    LOGGER.info("asn[ "+asn+" ] login success");
                }
                //添加session
                /*session.setAttribute("username",username);
                session.setAttribute("password",password);
                session.setAttribute("cookie",cookie);*/
            }
        }catch (Exception e){
            LOGGER.error("登录失败：",e);
            map.put("errMsg","系统错误"+e.getMessage());
        }
        return map;
    }

}
