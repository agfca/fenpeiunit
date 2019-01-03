package com.example.fenpeiunit.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ActiveX {
    private static String hCard = "0";
    //密钥索引
    private static String KEY_INDEX = "01";

    //ActiveX 控件 名称
    private static String ocx = "COMLoadCtrl_Portal.LoadCtrl";
    private static Dispatch cardCom = ActiveX.cardCom();

    //获取 activeX 对象
    public static Dispatch cardCom(String... ocxName) {
        Dispatch disp = null;
        try{
            ActiveXComponent com = null;
            if(ocxName.length>0){
                com = new ActiveXComponent(ocxName[0]);
            }else{
                com = new ActiveXComponent(ocx);
            }
            disp = com.getObject();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("OCX库获取失败");
        }
        return disp;
    }

    // hCard 和 b  加密
    public static String internalAuth(String hCard, String b){
        String result = Dispatch.call( cardCom, "internalAuth",
                new Variant(hCard),new Variant(b)
        ).getString();
        return result;
    }

    // 释放句柄
    public static void releaseCard(String hCard){
        Dispatch.call( cardCom, "releaseCard",
                new Variant(hCard)
        ).getString();
    }

    //getAID
    public static String getAID(int index){
        String result = Dispatch.call(cardCom, "getAID",
                new Variant(index)
        ).getString();
        return result;
    }

    //密钥索引
    public static String setKeyIndex(int key_index){
        String result = Dispatch.call(cardCom, "setKeyIndex",
                new Variant(key_index)
        ).getString();
        return result;
    }

    public static String GetCard(String AID){
        String result = Dispatch.call(cardCom, "GetCard",
                new Variant(AID)
        ).getString();
        return result;
    }

    //addSupportReader
    public static String addSupportReader(String readerStr){
        String result = Dispatch.call(cardCom, "addSupportReader",
                new Variant(readerStr)
        ).getString();
        return result;
    }

    public static void addReaders() {
        if (cardCom!=null) {
            ActiveX.addSupportReader("E9EC281B1F92FABB0BA8334BE7DDD01A8295161808A0D3847611B4C8BADB4C05B38CFB6090708CCEBC2D2F6D6315289D7E8E7634067D99EC6555B5A18CED7E5EBFB73281C49F290A9C6AE44FEA18FFABC23EE17CEEA264740F25C3472364CC98");
            ActiveX.addSupportReader("E9EC281B1F92FABB0BA8334BE7DDD01A8295161808A0D3847611B4C8BADB4C05B38CFB6090708CCEBC2D2F6D6315289D7E8E7634067D99EC6555B5A18CED7E5EDB998BE6F32EF6139C6AE44FEA18FFABC23EE17CEEA264740F25C3472364CC98");
            ActiveX.addSupportReader("E9EC281B1F92FABB0BA8334BE7DDD01A8295161808A0D3847611B4C8BADB4C052091BECBB02493EDD98E94F717FD2A0A7E8E7634067D99EC6555B5A18CED7E5ECA888FDF52991E2A9C6AE44FEA18FFABC23EE17CEEA264740F25C3472364CC98");
            //CardMan老驱动识别名字是CardMan 5x21
            //model omnikey 5321v2 @2009     和      omnikey5321 r5321
            ActiveX.addSupportReader("055A2EF0771E79F3D25A874ADDE71D008295161808A0D3847611B4C8BADB4C052091BECBB02493ED44CE6625004CD72F872217C75A517992D1062AEC869B9CBBDDE35F50D5C743E43064281CE3AB16612E325EC24DA53A22");
            //cardman 5321 @2006
            ActiveX.addSupportReader("055A2EF0771E79F3D25A874ADDE71D008295161808A0D3847611B4C8BADB4C052091BECBB02493ED44CE6625004CD72F872217C75A5179926CC6301E062C6246DDE35F50D5C743E43064281CE3AB16612E325EC24DA53A22");

            //xp新驱动识别名字是OMNIKEY 5x21
            //model omnikey 5321v2 @2009 和 omnikey5321 r5321 和 omnikey5421 @2013
            ActiveX.addSupportReader("E9EC281B1F92FABB0BA8334BE7DDD01A8295161808A0D3847611B4C8BADB4C052091BECBB02493EDD98E94F717FD2A0A7E8E7634067D99EC6555B5A18CED7E5EDB998BE6F32EF6139C6AE44FEA18FFABC23EE17CEEA264740F25C3472364CC98");
            //cardman 5321 @2006
            ActiveX.addSupportReader("E9EC281B1F92FABB0BA8334BE7DDD01A8295161808A0D3847611B4C8BADB4C052091BECBB02493EDD98E94F717FD2A0A7E8E7634067D99EC6555B5A18CED7E5EBFB73281C49F290A9C6AE44FEA18FFABC23EE17CEEA264740F25C3472364CC98");

            //xp(装补丁)识别名字是USB Smart Card reader
            //model omnikey 5422 @2017
            ActiveX.addSupportReader("AC2B7383750AA07858ABAFBCA834210E476F27A1E537C978BB5EF38CDC985CB6C12429B33DF71F22FF253A63962C72F4A7605CE3467F0D70B91EE015FE092F5417F3F4F75849BB5DE6C4A1AC9AEFE572C958553B0D28BE2C4ABAE6D071FAE7397A6B0512D92869839C6AE44FEA18FFABC23EE17CEEA264740F25C3472364CC98");


            //xp以上用微软自带驱动  识别名字是Microsoft Usbccid Smartcard Reader (WUDF)
            //model omnikey 5422 @2017
            ActiveX.addSupportReader("C29D56A088DD9D0E0950A2704F942F4C283F023C15D26D6FBE4DC15C964945C6BC804048FB8D58B652AB0F6E3B40483C9F234D2AA4FEBE34384814B31F4C6AC71DAE9AB52894D009822CC57D7B94A9F6808596F1BC41028AE2C3B7666A2EACE08EDA21859A3EB5939BA2EF0FF926D22F7783CA14CB34073E46D1F78F805FAAAAA36F8978A6F09392ED36E4539EB7E1B1");
            //omnikey5421 @2013 和 omnikey5321 r5321 和 model omnikey 5321v2 @2009
            ActiveX.addSupportReader("C29D56A088DD9D0E0950A2704F942F4C283F023C15D26D6FBE4DC15C964945C6BC804048FB8D58B652AB0F6E3B40483C9F234D2AA4FEBE34384814B31F4C6AC71DAE9AB52894D009822CC57D7B94A9F6808596F1BC41028AE2C3B7666A2EACE08EDA21859A3EB5939BA2EF0FF926D22F7783CA14CB34073EC188A7A4C256DD49A36F8978A6F09392ED36E4539EB7E1B1");
            //cardman 5321 @2006
            ActiveX.addSupportReader("C29D56A088DD9D0E0950A2704F942F4C283F023C15D26D6FBE4DC15C964945C6BC804048FB8D58B652AB0F6E3B40483C9F234D2AA4FEBE34384814B31F4C6AC71DAE9AB52894D009822CC57D7B94A9F6808596F1BC41028AE2C3B7666A2EACE08EDA21859A3EB5939BA2EF0FF926D22F7783CA14CB34073E8C6793ADB01D7720A36F8978A6F09392ED36E4539EB7E1B1");
        }
    }

    // TODO 释放句柄    缓存？？？
    public static void releaseCard() { //释放句柄
        if (!"0".equals(hCard)) {
            ActiveX.releaseCard(hCard);
        }
    }

    public static String evalCardReturn(String rsp){
        rsp = rsp.trim();
        if(rsp.length()<2){
            return "0";
        }
        String prefix = rsp.substring(0, 2);
        if (prefix .equals("00")) {
            return rsp.substring(2);
        }else{
            return "0";
        }
    }

    public static void getApp(int index){
        addReaders();
        releaseCard();//释放句柄
        String data = null;
        try {
            //TODO getAID 返回是 00701C611A4F0FA000000003504554524F4348494E415007279000
            //data = evalCardReturn(ActiveX.getAID(index));
            data = "701C611A4F0FA000000003504554524F4348494E415007279000";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取驱动等失败");
        }
        //data += "00";//加两位  防止报错
        int len = Integer.parseInt(data.substring(10, 10 + 2), 16) * 2;

        //TODO  hCard  没用
        String AID = data.substring(12, 12 + len);
        ActiveX.setKeyIndex(Integer.parseInt(KEY_INDEX, 16)); //设置密钥索引
        //hCard = evalCardReturn(new String(ActiveX.GetCard(AID)));
        hCard = "-369033177";
    }



    public static String internalAuth(String s){
        getApp(1);
        /*
        * -369033213
        * */
        //if (hCard != 0 && s.length() > 0) {
        String c = "";
        if (s.length() > 0) {
            String a = Sha1.hex_sha1(s);
            if (a.length() == 40) {
                //var b = a.substr(0, 16);
                String b = a.substring(0, 16);

                c = ActiveX.internalAuth(hCard,b);

                b = a.substring( 16 , 16 + 16);

                c += ActiveX.internalAuth(hCard,b);

                b = a.substring(32) + "80000000";

                c += ActiveX.internalAuth(hCard,b);
            }
        }
        return c;
    }

    /*
     function saveTempLate() {
            try {
               // debugger;
                var resultPrice = true;
                var reqId = "0DF"
                window.top.getCard();
                var asn = window.top.getAsn();
                var map = {}
                var list = []
                $("#show_List").find("input[type="text"]").each(function (idx, ele) {
                    resultPrice = checkPrice(event, this);
                    if (!resultPrice) {
                        return false;
                    }
                    var amount = Format.yuanToFen(ele.value);
                    if (Format.yuanToFen(ele.name) != amount) {
                        map[ele.id] = amount;
                    }
                });
                if (!resultPrice) {
                    alert("金额有误！");
                    return true;
                }
                var mapStr = formatPara(map);
                var str = "" + reqId + asn + template[0] + (mapStr ? mapStr : "");
                var mapStrEncrypt = window.top.internalAuth(str);
     */

    //TODO  reqId = 0DF   零  不是 o  是不是变化的？  mapStr 未生效
    //mapStr 是  cardId=金额   清空为 ""
    //SavecancalPage()
    public static String saveTempLateEncrypt(String reqId, String asn, String templateId, String...mapStr){
        String str = "" + reqId + asn + templateId + "";
        return internalAuth(str);
    }

    //fenPei()
    public static String fenPeiEncrypt(String uniqueId, String asn, String templateId){
        String str = uniqueId + asn + templateId;
        return internalAuth(str);
    }




    public static void main(String[] args) {

        System.out.println(ActiveX.getAID(1));
    }
}
