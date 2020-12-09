package com.prashant.mywikipediaapp.Model;

import java.util.List;

public class FeaturedImagesModel {

    private String number;
    private String pageId;
    private String ns;
    private String title;
    private List<ImageInfo> imageInfoList;
    private String type;


    public FeaturedImagesModel(String number, String pageId, String ns, String title, List<ImageInfo> imageInfoList) {
        this.number = number;
        this.pageId = pageId;
        this.ns = ns;
        this.title = title;
        this.imageInfoList = imageInfoList;
    }

    public FeaturedImagesModel() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }
}
