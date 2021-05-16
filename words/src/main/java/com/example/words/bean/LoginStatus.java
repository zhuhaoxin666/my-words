package com.example.words.bean;

public class LoginStatus {
    private Integer code;
    private String message;
    private String name;

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
