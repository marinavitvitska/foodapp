package com.example.admin.food.Model;

import java.util.ArrayList;
import java.util.List;

public class Food {
    private String Name, Image, Description, Discount, MenuId, FoodId;

    public List<Ingredient> Ingredients;

    public Food() {
        Ingredients = new ArrayList<>();
    }

    public Food(String name, String image, String description, String discount,
            String menuId, String foodId, ArrayList<Ingredient> ingredients) {
        Name = name;
        Image = image;
        Description = description;
        Discount = discount;
        MenuId = menuId;
        FoodId = foodId;
        this.Ingredients = ingredients;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

}