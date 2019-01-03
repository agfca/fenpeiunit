package com.example.fenpeiunit.web;

import com.alibaba.fastjson.JSONObject;
import com.example.fenpeiunit.cons.Cons;
import com.example.fenpeiunit.cons.GlobalVar;
import com.example.fenpeiunit.cons.TemplateInfo;
import com.example.fenpeiunit.domain.CardUserInfo;
import com.example.fenpeiunit.domain.FenPeiResult;
import com.example.fenpeiunit.domain.JSession;
import com.example.fenpeiunit.domain.UserInfo;
import com.example.fenpeiunit.service.GetDateService;
import com.example.fenpeiunit.service.SaveDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EditDataController {

    @Autowired
    private GetDateService getDateService;
    @Autowired
    private SaveDateService saveDateService;
    @Autowired
    private GlobalVar globalVar;
    @Autowired
    private static Logger LOGGER = LoggerFactory.getLogger(EditDataController.class);


    /**
     * 获取所有油卡
     */
    @RequestMapping("/getCards")
    public Map<String,Object> getCards(HttpSession session, @RequestParam String parentCardNum){
        Map<String,Object> map = new HashMap<>();
        map.put("result","false");
        try{
            //获取主卡 cookie
            Map<String,JSession> varMap = globalVar.getVarMap();

            for(String s:varMap.keySet()){
                System.out.println("key : "+s+" cookie : "+varMap.get(s).getCookie()+";asn = "
                        +varMap.get(s).getAsn()+";username:"+varMap.get(s).getPassword()+";password:"+varMap.get(s).getPassword());
            }

            JSession jSession = varMap.get(parentCardNum);
            String cookie ;
            if(jSession!=null){
                cookie = jSession.getCookie();
            }else{
                map.put("errMsg",Cons.notLoginErrMsg);
                return map;
            }
            /*String cookie = (String) session.getAttribute("cookie");
            if(cookie==null){
                map.put("errMsg","未登录，请重新登录");
                return map;
            }
            //username password
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");*/

            //String cookie = "ASP.NET_SessionId=xmkyqv1jgt5muzmmi2d0cxxb";
            //获取  用户信息
            boolean isLogin = false;
            String templateId = "";
            do{
                //System.out.println("===============获取数据==================");
                UserInfo userInfo = getDateService.getUserInfo(cookie,parentCardNum);
                if(userInfo!=null){//登录
                    isLogin = true;
                    templateId = userInfo.getDefaultTemplate().getTemplateId();
                }
            }while (false); //TODO   油卡网站  超时  自动重新登录

            //已经登录   获取list
            if(isLogin){
                List<JSONObject> cardList = getDateService.getCardsByTemplateId(cookie,templateId);//cardList
                map.put("data",cardList);
                map.put("result","true");
            }else{
                throw new Exception("获取登录用户数据错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("获取油卡："+parentCardNum+"；子卡数据失败。",e);
            map.put("errMsg",e.getMessage());
        }
        return map;
    }


    /**
     * 修改 油卡 分配金额
     */
    @RequestMapping("/editCardsAmount")
    public Map<String,Object> editCardAmount(HttpSession session,
                                             @RequestParam String parentCardNum,
                                             @RequestParam String mapStr,
                                             @RequestParam String sumAmount){//总金额
        Map<String,Object> map = new HashMap<>();
        map.put("result","false");
        try{
            //获取主卡 cookie
            Map<String,JSession> varMap = globalVar.getVarMap();
            JSession jSession = varMap.get(parentCardNum);
            String cookie ;
            if(jSession!=null){
                cookie = jSession.getCookie();
            }else{
                map.put("errMsg", Cons.notLoginErrMsg);
                return map;
            }
            /*String cookie = (String) session.getAttribute("cookie");
            if(cookie==null){
                map.put("errMsg","未登录，请重新登录");
                return map;
            }*/
            //用户信息  模板金额
            UserInfo userInfo = getDateService.getUserInfo(cookie,parentCardNum);
            TemplateInfo templateInfo = userInfo.getDefaultTemplate();

            boolean isClear = false;
            if(!"0".equals(templateInfo.getTemplateAmount())){ //模板清零
                List<JSONObject> cardList = getDateService.getCardsByTemplateId(cookie,templateInfo.getTemplateId());//cardList

                StringBuffer mapStrClear = new StringBuffer();
                for (int i = 0; i < cardList.size(); i++){
                    CardUserInfo cardUserInfo =  JSONObject.toJavaObject(cardList.get(i),CardUserInfo.class);
                    mapStrClear.append(cardUserInfo.getCardUserId()+"=0;");//金额为零
                    if(i==cardList.size()-1||(i+1)%100==0){
                        //清空模板金额
                        saveDateService.editTemplateMoney(cookie,parentCardNum,templateInfo.getTemplateId(),mapStrClear.toString());
                        mapStrClear = new StringBuffer();
                    }
                }

                //查询是否清空
                userInfo = getDateService.getUserInfo(cookie,parentCardNum);
                templateInfo = userInfo.getDefaultTemplate();
                if("0".equals(templateInfo.getTemplateAmount())){
                    isClear = true;
                }
            }else{
                isClear = true;
            }

            if(!isClear){//未清空
                throw new Exception("清空金额失败，主卡："+parentCardNum);
            }
            //mapStr=153056362=1;153057316=1;153057317=1;153057318=1;153057319=1;153057320=1
            //String mapStr = "153056362=0;153057316=0;153057317=0;153057318=0;153057319=0;153057320=0;";
            saveDateService.editTemplateMoney(cookie,parentCardNum,templateInfo.getTemplateId(),mapStr);

            //查询 当前模板 总金额 amount
            userInfo = getDateService.getUserInfo(cookie,parentCardNum);
            templateInfo = userInfo.getDefaultTemplate();
            if(!templateInfo.getTemplateAmount().equals(sumAmount)){//总金额
                throw new Exception("分配金额错误，实际金额："
                        + templateInfo.getTemplateAmount()
                        + "，参数金额："+sumAmount
                );
            }
            //修改模板成功
            //分配金额
            FenPeiResult fenPeiResult = saveDateService.confirmFenPei(cookie,parentCardNum,templateInfo.getTemplateId());
            map.put("result","true");
            map.put("batchNo",fenPeiResult.getBatchNo());
            map.put("blance",fenPeiResult.getBlance());
            LOGGER.info("asn[ "+parentCardNum+" ] batchNo[ "+fenPeiResult.getBatchNo()+" ] balance[ "+fenPeiResult.getBlance()+" ] mapStr[ "+mapStr+" ]");
        }catch (Exception e){
            LOGGER.error("分配油卡金额错误：",e);
            map.put("errMsg",e.getMessage());
        }
        return  map;
    }

}


