package com.example.words.bean;

public class UserInfo {

    private Integer age;
    private String motto;
    private String name;

    @Override
    public String toString() {
        return "UserInfo{" +
                "age=" + age +
                ", motto='" + motto + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
