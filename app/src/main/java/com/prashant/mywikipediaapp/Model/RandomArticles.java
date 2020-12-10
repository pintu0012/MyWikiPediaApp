package com.prashant.mywikipediaapp.Model;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class RandomArticles {
    private String pageId;
    private String title;
    private String type;
    private String extract;
    private List<ImageInfo> imageInfoList;
    private ArrayList<AllCategories> categoryModelArrayList;
    private String category;
    private String imageUrl;
    private boolean isSaved;
    private byte[] imageByte;

    public RandomArticles(String pageId, String title, String type, String imageUrl, String category, String extract, boolean isSaved) {
        this.pageId = pageId;
        this.title = title;
        this.type = type;
        this.imageUrl = imageUrl;
        this.category = category;
        this.extract = extract;
        this.isSaved = isSaved;
    }


    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<AllCategories> getCategoryModelArrayList() {
        return categoryModelArrayList;
    }

    public void setCategoryModelArrayList(ArrayList<AllCategories> categoryModelArrayList) {
        this.categoryModelArrayList = categoryModelArrayList;
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RandomArticles() {
    }


    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
