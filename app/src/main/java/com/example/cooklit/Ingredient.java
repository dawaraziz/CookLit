package com.example.cooklit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;
    public Ingredient (@NonNull String name) {this.mName=name;}
    public String getName(){return this.mName;}
}
