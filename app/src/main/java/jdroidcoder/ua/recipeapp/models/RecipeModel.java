package jdroidcoder.ua.recipeapp.models;

import java.io.Serializable;

/**
 * Created by jdroidcoder on 25.01.17.
 */

public class RecipeModel implements Serializable{
    private static final Long serialVersionUID =1l;
    private String code;
    private String brand;
    private String foodCategory;
    private String time;
    private String food;
    private String ingredients;
    private String methods;

    public RecipeModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "brand='" + brand + '\'' +
                ", foodCategory='" + foodCategory + '\'' +
                ", time='" + time + '\'' +
                ", food='" + food + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", methods='" + methods + '\'' +
                '}';
    }
}
