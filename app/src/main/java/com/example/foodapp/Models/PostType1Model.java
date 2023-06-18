package com.example.foodapp.Models;

public class PostType1Model {
    private String ID;
    private int IMG;
    private String Title;
    private String Ingredients;
    private String Price;
    private String Description;
    private String Category;

    public PostType1Model(int IMG, String title, String ingredients, String price, String description, String category) {
        this.IMG = IMG;
        Title = title;
        Ingredients = ingredients;
        Price = price;
        Description = description;
        Category = category;
    }

    public PostType1Model(String ID, int IMG, String title, String ingredients, String price, String description, String category) {
        this.ID = ID;
        this.IMG = IMG;
        Title = title;
        Ingredients = ingredients;
        Price = price;
        Description = description;
        Category = category;
    }

    public PostType1Model() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getIMG() {
        return IMG;
    }

    public void setIMG(int IMG) {
        this.IMG = IMG;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
