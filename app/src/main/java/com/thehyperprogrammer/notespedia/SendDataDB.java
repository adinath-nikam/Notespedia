package com.thehyperprogrammer.notespedia;

public class SendDataDB {
    public String userName;
    public String userEmail;
    public String userPhoneNumber;
    public String userGender;
    public  String userDepartment;
    public  String userSemester;

    public SendDataDB(){

    }

    public SendDataDB(String userName, String userEmail, String userPhoneNumber, String userGender, String userDepartment, String userSemester) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userGender = userGender;
        this.userDepartment = userDepartment;
        this.userSemester = userSemester;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }


    public String getUserGender() {
        return userGender;
    }
    public String getUserDepartment() {
        return userDepartment;
    }
    public String getUserSemester() {
        return userSemester;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserPhoneNumber(String userPhoneNumber) { this.userPhoneNumber = userPhoneNumber;}
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public void setUserDepartment(String userDepartment) { this.userDepartment = userDepartment;}
    public void setUserSemester(String userSemester) { this.userSemester = userSemester;}


}
