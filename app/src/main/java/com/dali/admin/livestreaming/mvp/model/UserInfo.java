package com.dali.admin.livestreaming.mvp.model;

import com.dali.admin.livestreaming.http.IDontObfuscate;

/**
 * Created by dali on 2017/4/9.
 */

public class UserInfo extends IDontObfuscate{

    private String nickname;
    private int sex;
    private String headPic;
    private String sigId;
    private String userId;
    private String sdkAppId;
    private String sdkAccountType;
    private String token;

    public UserInfo() {
    }

    public UserInfo(String nickname, int sex, String headPic, String userId) {
        this.nickname = nickname;
        this.sex = sex;
        this.headPic = headPic;
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSigId() {
        return sigId;
    }

    public void setSigId(String sigId) {
        this.sigId = sigId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public String getSdkAccountType() {
        return sdkAccountType;
    }

    public void setSdkAccountType(String sdkAccountType) {
        this.sdkAccountType = sdkAccountType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", headPic='" + headPic + '\'' +
                ", sigId='" + sigId + '\'' +
                ", userId='" + userId + '\'' +
                ", sdkAppId='" + sdkAppId + '\'' +
                ", sdkAccountType='" + sdkAccountType + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
