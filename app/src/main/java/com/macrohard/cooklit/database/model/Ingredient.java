package com.macrohard.cooklit.database.model;

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

    @NonNull
    @ColumnInfo(name = "quantity")
    private String mQuantity;


    public Ingredient (@NonNull String name, @NonNull String quantity) {
        this.mName=name;
        this.mQuantity=quantity;
    }

    public String getName(){return this.mName;}
    public void setName(String name) {this.mName=name;}
    public String getQuantity() {return this.mQuantity;}
    public void setQuantity(String quantity) {this.mQuantity=quantity;}
}
