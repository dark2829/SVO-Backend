package com.svo.svo.model;

public class EmailValuesDTO {
    private String mailTo;


    public EmailValuesDTO() {

    }

    public EmailValuesDTO(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

}
