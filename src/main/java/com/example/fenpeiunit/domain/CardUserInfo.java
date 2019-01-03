package com.example.fenpeiunit.domain;

public class CardUserInfo {

    private Integer id;
    private String cardUserId;              //id
    private String asn;                     //卡号
    private String blance;                  //balance  卡余额（元）
    private String cardBlance;              //备用金余额（元）

    private String amount;                  //分配金额（元）

    private String lastModifiedTime;        //最后更新时间

    private String userName;                //用户
    private String userType;                //司机账户
    private String licensePlateNumber;      //车牌
    private String cardStatus;              //正常

    //getter and setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardUserId() {
        return cardUserId;
    }

    public void setCardUserId(String cardUserId) {
        this.cardUserId = cardUserId;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getBlance() {
        return blance;
    }

    public void setBlance(String blance) {
        this.blance = blance;
    }

    public String getCardBlance() {
        return cardBlance;
    }

    public void setCardBlance(String cardBlance) {
        this.cardBlance = cardBlance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}
