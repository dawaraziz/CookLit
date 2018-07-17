package com.macrohard.cooklit.database.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "recipe_table")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "uri")
    private String uri;
    // in json format string
    @NonNull
    @ColumnInfo(name = "day")
    private String  day;
    @NonNull
    @ColumnInfo(name = "time")
    private String time;
    @NonNull
    @ColumnInfo(name = "repeat")
    private boolean repeat;


    public Recipe(int id, String name, String uri){
        this.id=id;
        this.name = name;
        this.uri = uri;
    }

    @Ignore
    public Recipe(int id, String name, String uri, String day, String time, boolean repeat){
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.day = day;
        this.time = time;
        this.repeat = repeat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id= id;
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

    public String getDay() {
        return day;
    }

    public void setDay(String date) {
        this.day = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }


}
