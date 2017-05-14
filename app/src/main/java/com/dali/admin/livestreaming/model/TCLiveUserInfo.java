package com.dali.admin.livestreaming.model;

import java.io.Serializable;

/**
 * Created by dali on 2017/5/7.
 */

public class TCLiveUserInfo implements Serializable {
    private String userId;
    private String nickname;
    private String headPic;
    private String location;

    public TCLiveUserInfo(String userId, String nickname, String headPic, String location) {
        this.userId = userId;
        this.nickname = nickname;
        this.headPic = headPic;
        this.location = location;
    }

    public TCLiveUserInfo() {
    }

    @Override
    public String toString() {
        return "TCLiveUserInfo{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headPic='" + headPic + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
