package com.example.fenpeiunit.domain;

public class FenPeiResult {
    private Integer id;
    private String blance;             //余额
    private String batchNo;             //单号



    //getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBlance() {
        return blance;
    }

    public void setBlance(String blance) {
        this.blance = blance;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public FenPeiResult(String blance, String batchNo){
        this.blance = blance;
        this.batchNo = batchNo;
    }
}
