package com.example.admin.food.Model;

public class Ingredient {

    public String id;

    public int count;

    public String name;

    public String parts;

    public Ingredient() {
    }

    public Ingredient(final int count, final String name, final String parts) {
        this.count = count;
        this.name = name;
        this.parts = parts;
    }
}
