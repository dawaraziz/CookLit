package com.macrohard.cooklit.database.model;

/**
 * Created by qianwu on 2018-05-23.
 */

public class Recipe {

    private String name;
    private String uri;

    public Recipe(String name, String uri){
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
