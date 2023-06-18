package com.example.foodapp.Models;

public class CategoryModel {
    private String Title;
    private int ICON;

    public CategoryModel(String title, int ICON) {
        Title = title;
        this.ICON = ICON;
    }

    public CategoryModel() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getICON() {
        return ICON;
    }

    public void setICON(int ICON) {
        this.ICON = ICON;
    }
}
