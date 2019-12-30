package com.thehyperprogrammer.notespedia;

public class MessagesModel {

    String Date,Time,Message,Name,Uid;

    public MessagesModel() {
    }

    public MessagesModel(String date, String time, String message, String name,String uid) {
        this.Date = date;
        this.Time = time;
        this.Message = message;
        this.Name = name;
        this.Uid = uid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}
