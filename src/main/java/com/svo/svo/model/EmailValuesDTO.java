package com.svo.svo.model;

public class EmailValuesDTO {
    private String mailTo;
    private String userName;
    private String jwt;


    public EmailValuesDTO(){

    }

    public EmailValuesDTO(String mailTo, String userName, String jwt) {
        this.mailTo = mailTo;
        this.userName = userName;
        this.jwt = jwt;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
