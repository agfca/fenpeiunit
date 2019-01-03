package com.example.fenpeiunit.cons;

public class TemplateInfo {
    private String templateId = "";
    private String templateAmount = "";
    public TemplateInfo(){
    }
    public TemplateInfo(String templateId, String templateAmount){
        this.templateId = templateId;
        this.templateAmount = templateAmount;
    }

    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getTemplateAmount() {
        return templateAmount;
    }
    public void setTemplateAmount(String templateAmount) {
        this.templateAmount = templateAmount;
    }
}
