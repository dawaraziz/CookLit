package com.macrohard.cooklit.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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

    @NonNull
    @ColumnInfo(name = "expirydate")
    private String mExpiryDate;

    @Ignore
    Boolean selected = false;

    public Ingredient (@NonNull String mName, @NonNull String mQuantity, @NonNull String mExpiryDate) {
        this.mName=mName;
        this.mQuantity=mQuantity;
        this.mExpiryDate=mExpiryDate;
    }

    public String getName(){return this.mName;}
    public void setName(String name) {this.mName=name;}
    public String getQuantity() {return this.mQuantity;}
    public void setQuantity(String quantity) {this.mQuantity=quantity;}
    public String getExpiryDate() {return this.mExpiryDate;}
    public void setExpiryDate (String expirydate) {this.mExpiryDate=expirydate;}

    // checkbox
    public boolean getSelected(){
        return selected;
    }

    public void setSelected(boolean select){
        selected = select;
    }

}
