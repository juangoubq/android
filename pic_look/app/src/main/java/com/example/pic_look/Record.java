package com.example.pic_look;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Record {
    @SerializedName("id")
    private String id;
    @SerializedName("pUserId")
    private String pUserId;
    @SerializedName("imageCode")
    private String imageCode;
    @SerializedName("title")
    private String title;
    @SerializedName("introduce")
    private String introduce;
    @SerializedName("content")
    private String content;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("imageUrlList")
    private List<String> imageUrlList;
    @SerializedName("likeId")
    private Object likeId;
    @SerializedName("likeNum")
    private Object likeNum;
    @SerializedName("hasLike")
    private boolean hasLike;
    @SerializedName("collectId")
    private String collectId;
    @SerializedName("collectNum")
    private Object collectNum;
    @SerializedName("hasCollect")
    private boolean hasCollect;
    @SerializedName("hasFocus")
    private boolean hasFocus;
    @SerializedName("username")
    private String username;
    @SerializedName("avatar")
    private Object avatar;

    public String getId() {
        return id;
    }

    public String getPUserId() {
        return pUserId;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public Object getLikeId() {
        return likeId;
    }

    public Object getLikeNum() {
        return likeNum;
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public String getCollectId() {
        return collectId;
    }

    public Object getCollectNum() {
        return collectNum;
    }

    public boolean isHasCollect() {
        return hasCollect;
    }

    public boolean isHasFocus() {
        return hasFocus;
    }

    public String getUsername() {
        return username;
    }

    public Object getAvatar() {
        return avatar;
    }

    // 新增点赞相关的方法
    public int getLikeCount() {
        if (likeNum instanceof Integer) {
            return (Integer) likeNum;
        }
        return 0;
    }

    public void incrementLikeCount() {
        likeNum = getLikeCount() + 1;
    }

    public void decrementLikeCount() {
        likeNum = getLikeCount() - 1;
    }

    public boolean isLiked() {
        return hasLike;
    }

    public void toggleLike() {
        hasLike = !hasLike;
    }

    public String getIntroduce() {
        return introduce;
    }
}
