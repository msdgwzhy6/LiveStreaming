package com.dali.admin.livestreaming.model;

import java.io.Serializable;

/**
 * Created by dali on 2017/5/7.
 */

public class LiveInfo implements Serializable {
    private String liveId;
    private String title;
    private String groupId;
    private String playUrl;
    private String liveCover;
    private int viewCount;
    private int likeCount;
    private int createTime;
    //TCLiveUserInfo
    public TCLiveUserInfo userInfo;

    public LiveInfo(String groupId, String liveId, int createTime, int type, int viewCount, int likeCount, String title, String playUrl, String fileId, String liveCover, TCLiveUserInfo userInfo) {
        this.groupId = groupId;
        this.liveId = liveId;
        this.createTime = createTime;
        this.type = type;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.title = title;
        this.playUrl = playUrl;
        this.fileId = fileId;
        this.liveCover = liveCover;
        this.userInfo = userInfo;
    }

    private int type;
    private String fileId;

    @Override
    public String toString() {
        return "LiveInfo{" +
                ", groupId='" + groupId + '\'' +
                ", liveId='" + liveId + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", title='" + title + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", fileId='" + fileId + '\'' +
                ", liveCover='" + liveCover + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
    }

    public TCLiveUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(TCLiveUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
