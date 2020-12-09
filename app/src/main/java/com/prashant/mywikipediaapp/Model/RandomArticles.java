package com.prashant.mywikipediaapp.Model;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class RandomArticles {
    private String number;
    private String pageId;
    private String ns;
    private String title;
    private List<Revisions> revisionsList;
    private String type;
    private String extract;
    private List<ImageInfo> imageInfoList;
    private ArrayList<AllCategories> categoryModelArrayList;
    private String category;


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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Revisions> getRevisionsList() {
        return revisionsList;
    }

    public void setRevisionsList(List<Revisions> revisionsList) {
        this.revisionsList = revisionsList;
    }
}
