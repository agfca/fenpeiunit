package com.example.fenpeiunit.cons;

import com.example.fenpeiunit.domain.JSession;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "globalVar")
public class GlobalVar<T> {

    private Map<String,JSession> varMap = new HashMap<>();

    public Map<String,JSession> getVarMap() {
        return varMap;
	}

	public void setVarMap(Map varMap) {
		this.varMap = varMap;
	}
    public void setVarMap(String asn, String username, String password, String cookie) {
        JSession jSession = new JSession(asn,username, password, cookie);
        this.varMap.put(asn, jSession);
    }


    // 业务信息
    private T bizInfo;
    // 当前业务不需要处理，但是后续业务可能需要处理的信息
    //private String transInfo;
	public T getBizInfo() {
        return bizInfo;
    }

    public void setBizInfo(T bizInfo) {
        this.bizInfo = bizInfo;
    }
}
