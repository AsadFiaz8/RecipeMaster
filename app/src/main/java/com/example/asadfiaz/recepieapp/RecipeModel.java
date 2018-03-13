package com.example.asadfiaz.recepieapp;

/**
 * Created by Asad Fiaz on 1/31/2018.
 */

public class RecipeModel {

    private String title;
    private String ingredients;
    private String url;
    private String poster;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getUrl()
    {
        return url;
    }

    public String getPoster() {
        return poster;
    }
}
