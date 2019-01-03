package com.example.fenpeiunit.cons;

public interface Cons {
    //URL路径
    String webUrl = "https://www.95504.net/";
    //String webUrl = "http://www.95504.net/NewKljyk/Js_YkJs.aspx";
    String codeImageUrl = "https://www.95504.net/UserControl/Image.aspx?ucode=ucode";

    int webTimeout = 60000;

    //get card List url
    String dataUrl = "https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx";

    //验证码识别
    String imagePath = "D:/www/LiLangSoft.WebApi/imagecode/123456.gif";
    String image2StrUrl = "http://localhost/Home/GetCodeByFile";
    //http://localhost/Home/GetCodeByFile?url=D:/www/LiLangSoft.WebApi/imagecode/123456.gif

    String usernameInput = "UserLogin1_txtuserid";
    String passwordInput = "UserLogin1_txtpwd";
    String codeInput = "UserLogin1_txtcode";
    //String codeImageInput = "img_checkcode";
    String submitInput = "UserLogin1_btnLogin";
    String welcomeInput = "UserLogin1_lblName";
    //页面常用 元素
    /*String usernameInput = "ContentPlaceHolder1_UserLogin1_txtuserid";
    String passwordInput = "ContentPlaceHolder1_UserLogin1_txtpwd";
    String codeInput = "ContentPlaceHolder1_UserLogin1_txtcode";
    //String codeImageInput = "img_checkcode";
    String submitInput = "ContentPlaceHolder1_UserLogin1_btnLogin";
    String welcomeInput = "ContentPlaceHolder1_UserLogin1_lblName";*/


    String notLoginErrMsg = "请重新登录";
    String defaultTemplate = "默认模板";

    String reqId = "0DF";


    //pageSize  TODO
    int pageSize = 5000;


    //cookie
    String cookieKey = "ASP.NET_SessionId";
    String cookieValue = "";
    String headerField = "SET-COOKIE";

    //正则  获取失败提示信息
    String regTip = "//<!\\[CDATA\\[\\s*\\$\\.prompt\\s*\\(['\"]([^'\"]+)[^\\$]+//\\]\\]>";//\b  //<![CDATA[   .|\s *    '验证码错误'   .|\s*    //]]>
    //正则  获取 标识符
    String regUniqueId = "var\\s+uniqueId\\s*=\\s*['\"]\\d{9,12}['\"];\\s+uniqueId\\s*=\\s*['\"](\\d{9,12})['\"];";

    //超时时间/* connectTimeout：设置连接主机超时（单位：毫秒）readTimeout：设置从主机读取数据超时（单位：毫秒）*/
    int connectTimeout = 30000;
    int readTimeout = 60000;



}
