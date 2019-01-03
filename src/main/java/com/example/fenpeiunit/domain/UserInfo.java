package com.example.fenpeiunit.domain;

import com.example.fenpeiunit.cons.TemplateInfo;

public class UserInfo {

    private Integer id;
    private String companyUniqueId;       //单位编号
    private String companyName;             //单位名称
    private String idno;                    // 单位证件号
    private String userStatus;             //正常
    private String cardType;                //单位卡(2009中油标准卡)


    private String masterCardAsn;          //主卡卡号
    //template:'3010798,默认模板,2018-12-25 11:40:31,0;3011949,test,2018-12-25 11:22:11,61000;',
    //61000（分）
    private String template;              //分配模板
    private String balance;             //余额

    private TemplateInfo defaultTemplate;

    //getter and setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUniqueId() {
        return companyUniqueId;
    }

    public void setCompanyUniqueId(String companyUniqueId) {
        this.companyUniqueId = companyUniqueId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMasterCardAsn() {
        return masterCardAsn;
    }

    public void setMasterCardAsn(String masterCardAsn) {
        this.masterCardAsn = masterCardAsn;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public TemplateInfo getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setDefaultTemplate(TemplateInfo defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }
}
