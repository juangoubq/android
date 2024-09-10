package com.example.pic_look;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Data {
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

    public int getTotal() {
        return total;
    }

    public int getSize() {
        return size;
    }

    public int getCurrent() {
        return current;
    }



    public List<DraftItem> getDrafts() {
        List<DraftItem> drafts = Collections.emptyList();
        return drafts;
    }
}
