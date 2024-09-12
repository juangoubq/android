package com.example.pic_look;

import java.util.List;

public class DraftItem {
    private String title;
    private String content;
    private List<String> imageUrlList;

    public DraftItem(String title, String content, List<String> imageUrlList) {
        this.title = title;
        this.content = content;
        this.imageUrlList = imageUrlList;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }
}
