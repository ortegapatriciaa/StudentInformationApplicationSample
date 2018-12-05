package com.ortegapatriciaa.application_2;

/**
 * Created by ortegapatriciaa on 12/4/2018.
 */

public class AccountInfo {

    String userid;
    String name;
    String email;
    String birthdate;
    String username;
    String password;

    public AccountInfo(){

    }

    public AccountInfo(String userid, String name, String email, String birthdate, String username, String password){
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

}
