package com.example.myapplication;

public class UserData {
    public String fname, email, gender, fmember, age, phoneno;
    public UserData(){

    }

    public UserData(String fname, String email, String gender, String fmember, String age, String phoneno) {
        this.fname = fname;
        this.email = email;
        this.gender = gender;
        this.fmember = fmember;
        this.age = age;
        this.phoneno = phoneno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFmember() {
        return fmember;
    }

    public void setFmember(String fmember) {
        this.fmember = fmember;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}