package com.example.fenpeiunit.service;

import com.alibaba.fastjson.JSONObject;
import com.example.fenpeiunit.cons.Cons;
import com.example.fenpeiunit.cons.TemplateInfo;
import com.example.fenpeiunit.domain.UserInfo;
import com.example.fenpeiunit.util.HttpUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDateService {



    /**
     * https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx?
     id=1&asn=9130010005108801&date=0&date1=0
     { result:'0',errorMsg:'请重新登录'}

     { result:'1',errorMsg:'',
     data:[{
     companyUniqueId:'1002476499',
     masterCardAsn:'9130010005108801',
     companyName:'重庆加多惠网络科技有限公司',
     template:'3010798,默认模板,2018-12-25 11:40:31,0;3011949,test,2018-12-25 11:22:11,61000;',
     userStatus:'正常',
     cardType:'单位卡(2009中油标准卡)',
     idno:'91500117MA6057WK16',
     balance:'4710.00'
     }]
     */
    /*获取 主卡信息  +  默认模板 */
    public UserInfo getUserInfo(String cookie, String parentCardNum) throws Exception {
        UserInfo userInfo = null;
        String dataText = HttpUtils.sendGet(Cons.dataUrl,cookie
                ,"id=1"
                ,"asn="+parentCardNum
        );
        //返回“”  或者  数据
        if(!"".equals(dataText)){
            JSONObject job = null;
            try {
                job = JSONObject.parseObject(dataText);
            }catch (Exception e){
                throw new Exception(dataText);
            }

            //返回结果
            String returnResult = (String)job.get("result");
            String returnErrorMsg = (String)job.get("errorMsg");
            if("1".equals(returnResult)){//  获取数据成功
                List<JSONObject> tempList = (List<JSONObject>)job.get("data");//模板 list
                userInfo = JSONObject.toJavaObject(tempList.get(0),UserInfo.class);

                TemplateInfo templateInfo = getTemplateInfo(userInfo.getTemplate());
                userInfo.setDefaultTemplate(templateInfo);

                //获取到 templateId;  登录成功    请重新登录
            }else if("0".equals(returnResult)&&Cons.notLoginErrMsg.equals(returnErrorMsg)){//需要重新登录
                throw new Exception(Cons.notLoginErrMsg);
                //TODO 重新登录  跳转
            }else{
                throw new Exception("获取数据失败>>>石油登录用户信息");
            }
        }else{
            throw new Exception("访问URL错误>>>石油登录用户信息");
        }
        return userInfo;
    }

    /**
     https://www.95504.net/NewKljyk/Card_Info.ashx?
     fill=0.0351&InfoType=C_Cardlist&pagesize=15&currpage=1&cardAsn=&cardStatus=&1=1

     {usertype:'2',issuccessed:'true',pageCount:1,rowCount:6,
     data:[
     {driverName:'',asn:'9130010005108801',isMaster:'是',cardStatus:'正常状态'},
     {driverName:'',asn:'9130010005108802',isMaster:'--',cardStatus:'正常状态'},
     {driverName:'',asn:'9130010005108803',isMaster:'--',cardStatus:'正常状态'},
     {driverName:'',asn:'9130010005108804',isMaster:'--',cardStatus:'正常状态'},
     {driverName:'',asn:'9130010005108805',isMaster:'--',cardStatus:'正常状态'},
     {driverName:'',asn:'9130010005108806',isMaster:'--',cardStatus:'正常状态'}
     ]}
     */
    /*直接获取子卡信息*/
    public String getCards(String cookie){
        String url = "https://www.95504.net/NewKljyk/Card_Info.ashx";
        String dataText = HttpUtils.sendGet(url,cookie
                ,"fill="+ Math.random()
                ,"InfoType=C_Cardlist"
                ,"pagesize="+Cons.pageSize
                ,"currpage=1"
                ,"cardAsn="
                ,"cardStatus="
        );
        return dataText;
    }

    /**
     https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx?id=4&templateId=3011949&pageSize=100&currPage=1&date=0.8403783929036888&date1=0.788338448707099

     {acount:'610.00',
     result:'1',
     errorMsg:'',
     pageCount:1,
     rowCount:6, data:[{
     cardUserId:'153056362',userType:'司机帐户',asn:'9130010005108801',userName:'-',licensePlateNumber:'-',blance:'0.00',cardBlance:'0.00',cardStatus:'正常',amount:'10.00',lastModifiedTime:'1970-01-01 08:00:00'},
     {cardUserId:'153057316',userType:'司机帐户',asn:'9130010005108802',userName:'-',licensePlateNumber:'-',blance:'60.00',cardBlance:'0.00',cardStatus:'正常',amount:'100.00',lastModifiedTime:'2018-12-24 16:47:28'},
     {cardUserId:'153057317',userType:'司机帐户',asn:'9130010005108803',userName:'-',licensePlateNumber:'-',blance:'20.00',cardBlance:'0.00',cardStatus:'正常',amount:'500.00',lastModifiedTime:'2018-12-24 09:49:03'},
     {cardUserId:'153057318',userType:'司机帐户',asn:'9130010005108804',userName:'-',licensePlateNumber:'-',blance:'10.00',cardBlance:'0.00',cardStatus:'正常',amount:'0',lastModifiedTime:'2018-12-23 11:22:44'},
     {cardUserId:'153057319',userType:'司机帐户',asn:'9130010005108805',userName:'-',licensePlateNumber:'-',blance:'200.00',cardBlance:'0.00',cardStatus:'正常',amount:'0',lastModifiedTime:'2018-12-23 11:22:44'},
     {cardUserId:'153057320',userType:'司机帐户',asn:'9130010005108806',userName:'-',licensePlateNumber:'-',blance:'0.00',cardBlance:'0.00',cardStatus:'正常',amount:'0',lastModifiedTime:'1970-01-01 08:00:00'}
     ]}
     */
    /*根据模板 获取 所有 子卡信息   模板一共  一共 分配金额 */
    public List<JSONObject> getCardsByTemplateId(String cookie, String templateId) throws Exception {
        List<JSONObject> cardList = null;
        String dataText = HttpUtils.sendGet(Cons.dataUrl,cookie
                ,"id=4"
                ,"templateId="+templateId
                ,"pageSize="+Cons.pageSize
                ,"currPage=1"
        );
        if(!"".equals(dataText)){
            JSONObject job = JSONObject.parseObject(dataText);
            //返回结果
            String returnResult = (String)job.get("result");
            String returnErrorMsg = (String)job.get("errorMsg");

            if("1".equals(returnResult)){//  获取数据成功
                cardList = (List<JSONObject>)job.get("data");//cardList
            }else if("0".equals(returnResult)&&Cons.notLoginErrMsg.equals(returnErrorMsg)){//需要重新登录
                throw new Exception(Cons.notLoginErrMsg);
                //TODO 重新登录  跳转
            }else{
                throw new Exception("获取数据失败>>>根据模板获取子卡分配列表");
            }
        }else{
            throw new Exception("访问URL错误>>>根据模板获取子卡分配列表");
        }
        return cardList;
    }

    /*获取  模板 金额分配 */
    //TODO  不清楚是否获取全部 数据  分页
    public String getTemplateAccount(String cookie, String asn , String templateId){
        /*
        https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx
        ?id=2&templateId=3010798&currPage=1
        &date=0.7405520565287805&date1=0.48983914819188806

        {acount:'0.08',result:'1',errorMsg:'',pageCount:1,rowCount:6,
        data:[
        {userName:'--',asn:'9130010005108801',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'0.02',amount:'0.03',lastModifiedTime:'2018-12-26 10:19:46'},
        {userName:'--',asn:'9130010005108805',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'200.02',amount:'0.01',lastModifiedTime:'2018-12-26 10:19:45'},
        {userName:'--',asn:'9130010005108806',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'0.02',amount:'0.01',lastModifiedTime:'2018-12-26 10:19:45'},
        {userName:'--',asn:'9130010005108803',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'20.02',amount:'0.01',lastModifiedTime:'2018-12-26 10:19:46'},
        {userName:'--',asn:'9130010005108804',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'10.02',amount:'0.01',lastModifiedTime:'2018-12-26 10:19:46'},
        {userName:'--',asn:'9130010005108802',licensePlateNumber:'--',cardStatus:'正常',userStatus:'正常',cardBlance:'0.00',blance:'60.03',amount:'0.01',lastModifiedTime:'2018-12-26 10:19:45'}
        ]}
        */
        String url = "https://www.95504.net/NewBigCustomerTerminal/NewDistributionRead.ashx";
        String dataText = HttpUtils.sendGet(Cons.dataUrl,cookie
                ,"id=2"
                ,"templateId="+templateId
                ,"asn="+asn
                ,"currPage=1"//TODO  不清楚是否获取全部 数据  分页
        );
        return dataText;
    }

    public static TemplateInfo getTemplateInfo(String templates){
        TemplateInfo templateInfo = new TemplateInfo();
        //template:'3010798,默认模板,2018-12-25 11:40:31,0;3011949,test,2018-12-25 11:22:11,61000;',  时间倒序
        String[] templateArr = templates.split(";");
        for (String template:templateArr){
            if(template.indexOf(Cons.defaultTemplate)!=-1){
                String[] strArr = template.split(",");
                templateInfo.setTemplateId(strArr[0]);
                templateInfo.setTemplateAmount(strArr[3]);
            }
        }
        return templateInfo;
    }
}
