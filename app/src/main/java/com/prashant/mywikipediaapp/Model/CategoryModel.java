package com.prashant.mywikipediaapp.Model;

import java.util.ArrayList;

public class CategoryModel {
    private ContinueModel continueModel;
    private ArrayList<AllCategories> allCategoriesArrayList;

    public ContinueModel getContinueModel() {
        return continueModel;
    }

    public void setContinueModel(ContinueModel continueModel) {
        this.continueModel = continueModel;
    }

    public ArrayList<AllCategories> getAllCategoriesArrayList() {
        return allCategoriesArrayList;
    }

    public void setAllCategoriesArrayList(ArrayList<AllCategories> allCategoriesArrayList) {
        this.allCategoriesArrayList = allCategoriesArrayList;
    }
}
