package com.test.serialize;

import java.io.Serializable;

public class User implements Serializable{
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    private String username;
    private String nickname;
}
