package jdroidcoder.ua.recipeapp.models;

import java.io.Serializable;

/**
 * Created by jdroidcoder on 25.01.17.
 */

public class RecipeModel implements Serializable{
    private static final Long serialVersionUID =1l;
    private String name;
    private String code;
    private String brand;
    private String[] foodCategory;
    private String time;
    private String[] ingredients;
    private String[] methods;
    private String image;

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

    public String[] getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String[] foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "brand='" + brand + '\'' +
                ", foodCategory='" + foodCategory + '\'' +
                ", time='" + time + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", methods='" + methods + '\'' +
                '}';
    }
}
