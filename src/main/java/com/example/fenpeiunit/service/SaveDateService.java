package com.example.fenpeiunit.service;

import com.alibaba.fastjson.JSONObject;
import com.example.fenpeiunit.cons.Cons;
import com.example.fenpeiunit.domain.FenPeiResult;
import com.example.fenpeiunit.util.ActiveX;
import com.example.fenpeiunit.util.HttpUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SaveDateService {

    /**
     //清空  mapStr=
     https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx
     ?id=5&templateId=3011949&asn=9130010005108801&mapStr=&mapStrEncrypt=A67185695061CC1C4A61977AC52FA0FCA1B6D2BB3C93BE27
     &date=0.8026106227324354&date1=0.23571327695781574

     //分配  mapStr=153057316（cardId）=1000（分）
     https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx
     ?id=5&templateId=3011949&asn=9130010005108801
     &mapStr=153056362=1;153057316=1;153057317=1;153057318=1;153057319=1;153057320=1
     &mapStrEncrypt=2E2C26CDD476CCE5E43632CC9B94E15DFA9D0409F88A9C21
     &date=0.4956893180197453&date1=0.08113765950663376

     {result:'1',errorMsg:''}
     */
    /*修改模板子卡分配信息*/
    public boolean editTemplateMoney(String cookie, String asn, String templateId, String mapStr) throws Exception {
        boolean flag = false;
        String mapStrEncrypt = ActiveX.saveTempLateEncrypt(Cons.reqId,asn,templateId);
        String dataText = HttpUtils.sendGet(Cons.dataUrl,cookie
                ,"id=5"
                ,"templateId="+templateId
                ,"asn="+asn
                ,"mapStr="+mapStr//根据cardId  分配金额
                ,"mapStrEncrypt="+mapStrEncrypt
        );
        if(!"".equals(dataText)){
            JSONObject job = JSONObject.parseObject(dataText);
            //返回结果
            String returnResult = (String)job.get("result");
            String returnErrorMsg = (String)job.get("errorMsg");

            if("1".equals(returnResult)){// 修改成功
                flag = true;
            }else if("0".equals(returnResult)&&"请重新登录".equals(returnErrorMsg)){//需要重新登录
                throw new Exception("请重新登录>>>保存模板信息");
                //TODO 重新登录  跳转
            }else{
                throw new Exception("获取数据失败>>>保存模板信息");
            }
        }else{
            throw new Exception("访问URL错误>>>保存模板信息");
        }
        return flag;
    }

    /**
     //获取模板 分配 金额的 子卡
     https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx
     ?id=2&templateId=3010798&currPage=1&date=0.33512152509883497&date1=0.031946525365637135

     {acount:'0.03',result:'1',errorMsg:'',pageCount:1,rowCount:3,
     data:[
     {userName:'--',asn:'9130010005108803',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'20.03',amount:'0.01',lastModifiedTime:'2018-12-28 16:44:53'},
     {userName:'--',asn:'9130010005108802',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'60.04',amount:'0.01',lastModifiedTime:'2018-12-28 16:44:53'},
     {userName:'--',asn:'9130010005108801',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'0.04',amount:'0.01',lastModifiedTime:'2018-12-28 16:44:53'}
     ]}

     //确认分配 金额
     https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx
     ?id=3&asn=9130010005108801&templateId=3010798
     &mac=8F4B88CDB1D73A49CA2B7EE3CAD5FA3FD946060916770849
     &uniqueId=592338090
     &date=0.7212550423546806&date1=0.6672055424575014

     {result:'1',errorMsg:'',
     blance:'4709.86',batchNo:'110287046257',loyaltyBalance:'0'}
     */
    /*确认分配*/
    public FenPeiResult confirmFenPei(String cookie, String asn, String templateId) throws Exception {
        FenPeiResult fenPeiResult = null;

        String uniqueId = getUniqueId(cookie);
        if("".equals(uniqueId)){
            throw new Exception("获取分配金额时 uniqueId 错误");
        }
        //加密
        String mac = ActiveX.fenPeiEncrypt(uniqueId,asn,templateId);
        String dataText = HttpUtils.sendGet(Cons.dataUrl,cookie
                ,"id=3"
                ,"asn="+asn
                ,"templateId="+templateId
                ,"mac="+mac
                ,"uniqueId="+uniqueId
        );
        if(!"".equals(dataText)){
            JSONObject job = JSONObject.parseObject(dataText);
            //返回结果
            String returnResult = (String)job.get("result");
            String returnErrorMsg = (String)job.get("errorMsg");

            if("1".equals(returnResult)){// 修改成功
                /*{result:'1',errorMsg:'',blance:'4709.86',batchNo:'110287046257',loyaltyBalance:'0'}*/
                String blance = (String)job.get("blance");
                String batchNo = (String)job.get("batchNo");
                //剩余金额  单号
                fenPeiResult = new FenPeiResult(blance,batchNo);
            }else if("0".equals(returnResult)&&"请重新登录".equals(returnErrorMsg)){//需要重新登录
                throw new Exception("请重新登录>>>金额分配");
                //TODO 重新登录  跳转
            }else{
                throw new Exception("获取数据失败>>>金额分配");
            }
        }else{
            throw new Exception("访问URL错误>>>金额分配");
        }
        return fenPeiResult;
    }

    public String getUniqueId(String cookie) throws Exception {
        String uniqueId = "";
        String url = "https://www.95504.net/NewBigCustomerTerminal/UnitAccountDistributionReadCard.aspx";
        String htmlText = HttpUtils.sendGet(url,cookie);
        if(!"".equals(htmlText)){
            Pattern pattern = Pattern.compile(Cons.regUniqueId);
            Matcher m = pattern.matcher(htmlText);
            if (m.find(1)) {// 捕获
                uniqueId = m.group(1);
            }else{
                throw new Exception("正则uniqueId匹配失败>>>uniqueId获取页面");
            }
        }else{
            throw new Exception("访问URL错误>>>uniqueId获取页面");
        }
        return uniqueId;
    }
}
