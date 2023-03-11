package com.example.bdchat.Models;

public class MassageModel {

    String uId, massage,massageId;
    Long timestamp;




    public MassageModel(String uId, String massage, Long timestamp) {
        this.uId = uId;
        this.massage = massage;
        this.timestamp = timestamp;
    }

    public MassageModel(String uId, String massage) {
        this.uId = uId;
        this.massage = massage;
    }


    public MassageModel(){

    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getMassageId() {
        return massageId;
    }

    public void setMassageId(String massageId) {
        this.massageId = massageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
