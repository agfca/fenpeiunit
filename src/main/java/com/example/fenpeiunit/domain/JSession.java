package com.example.fenpeiunit.domain;


public class JSession {

    private String asn = "";
    private String username = "";        //账号，密码，cookie
    private String password = "";
    private String cookie = "";

    public JSession(String asn, String username, String password, String cookie){
        this.asn = asn;
        this.username = username;
        this.password = password;
        this.cookie = cookie;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
