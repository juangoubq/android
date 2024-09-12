package com.example.pic_look;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Data {
    @SerializedName("record")
    private Record record;
    @SerializedName("records")
    private List<Record> records;
    @SerializedName("total")
    private int total;
    @SerializedName("size")
    private int size;
    @SerializedName("current")
    private int current;
    @SerializedName("drafts")
    private List<DraftItem> drafts;
    @SerializedName("dynamicCount")
    private int dynamicCount;
    @SerializedName("followCount")
    private int followCount;

    public List<Record> getRecords() {
        return records;
    }

    public  Record getRecord() {
        return record;
    }

    public int getTotal() {
        return total;
    }

    public int getSize() {
        return size;
    }

    public int getCurrent() {
        return current;
    }

    private String id;
    private String appKey;
    private String username;
    private String password;
    private int sex;
    private String introduce;
    private String avatar;
    private String createTime;
    private String lastUpdateTime;
    private boolean isFocus = false;

    public String getUsername() {
        return username;
    }

    public String getIntroduce() {
        return introduce;
    }

    public List<DraftItem> getDrafts() {
        List<DraftItem> drafts = Collections.emptyList();
        return drafts;
    }

    public boolean isHasFocus() {
        return isFocus;
    }

    public void setHasFocus(boolean b) {
        isFocus = b;
        return ;
    }
}
