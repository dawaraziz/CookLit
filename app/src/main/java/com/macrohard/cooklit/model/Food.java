package com.macrohard.cooklit.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qianwu on 2018-05-21.
 */

public class Food {
    private String name;
    private String brandName;
    private String itemDescription;
    private int calories;
    private int caloriesFromFat;
    private int totalFat;
    private int saturatedFat;
    private int transFattyAcid;
    private int cholesterol;
    private int sodium;
    private int totalCarbohydrate;
    private int dietaryFibre;
    private int sugars;
    private int protein;
    private int vitaminADV;
    private int vitaminCDV;
    private int calciumDV;
    private int ironDV;
    private int servingWeightGrams;

    /*public int retriveInt(int i){
        if(i == null){

        }
    }*/
    public Food(JSONObject object) {

        try {

            this.name = object.getString("item_name");
            this.brandName = object.getString("brand_name");

//            this.itemDescription = object.getString("itemDescription");
//            this.calories = object.getInt("nf_calories");
//            this.caloriesFromFat = object.getInt("nf_caloriesFromFat");
//            this.totalFat = object.getInt("nf_totalFat");
//            this.saturatedFat = object.getInt("nf_saturatedFat");
//            this.transFattyAcid = object.getInt("nf_transFattyAcid");
//            this.cholesterol = object.getInt("nf_cholesterol");
//            this.sodium = object.getInt("nf_sodium");
//            this.totalCarbohydrate = object.getInt("nf_totalCarbohydrate");
//            this.dietaryFibre = object.getInt("nf_dietaryFibre");
//            this.sugars = object.getInt("nf_sugars");
//            this.protein = object.getInt("nf_protein");
//            this.vitaminADV = object.getInt("nf_vitaminADV");
//            this.vitaminCDV = object.getInt("nf_vitaminCDV");
//            this.calciumDV = object.getInt("nf_calciumDV");
//            this.ironDV = object.getInt("nf_ironDV");
//            this.servingWeightGrams = object.getInt("nf_servingWeightGrams")

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /*@Override
    public String toString() {
        return super.toString();
        return ""+name+" "+brandName+" "+itemDescription+" "+calories+ " " + caloriesFromFat+ " "
                +totalFat+" " + saturatedFat+ " "+ transFattyAcid+ " "+ cholesterol+ " "+ sodium
                + totalCarbohydrate+ " "+dietaryFibre+ " " + sugars+ " "+ protein+ " " + vitaminADV+ " "+
    }*/

    public Food(String name, String brandName, String itemDescription, int calories,
                int caloriesFromFat, int totalFat, int saturatedFat, int transFattyAcid,
                int cholesterol, int sodium, int totalCarbohydrate, int dietaryFibre, int sugars,
                int protein, int vitaminADV, int vitaminCDV, int calciumDV, int ironDV,
                int servingWeightGrams) {

        this.name = name;
        this.brandName = brandName;
        this.itemDescription = itemDescription;
        this.calories = calories;
        this.caloriesFromFat = caloriesFromFat;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.transFattyAcid = transFattyAcid;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.totalCarbohydrate = totalCarbohydrate;
        this.dietaryFibre = dietaryFibre;
        this.sugars = sugars;
        this.protein = protein;
        this.vitaminADV = vitaminADV;
        this.vitaminCDV = vitaminCDV;
        this.calciumDV = calciumDV;
        this.ironDV = ironDV;
        this.servingWeightGrams = servingWeightGrams;

    }

    void update(String name, String brandName, String itemDescription, int calories,
                int caloriesFromFat, int totalFat, int saturatedFat, int transFattyAcid,
                int cholesterol, int sodium, int totalCarbohydrate, int dietaryFibre, int sugars,
                int protein, int vitaminADV, int vitaminCDV, int calciumDV, int ironDV,
                int servingWeightGrams) {

        this.name = name;
        this.brandName = brandName;
        this.itemDescription = itemDescription;
        this.calories = calories;
        this.caloriesFromFat = caloriesFromFat;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.transFattyAcid = transFattyAcid;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.totalCarbohydrate = totalCarbohydrate;
        this.dietaryFibre = dietaryFibre;
        this.sugars = sugars;
        this.protein = protein;
        this.vitaminADV = vitaminADV;
        this.vitaminCDV = vitaminCDV;
        this.calciumDV = calciumDV;
        this.ironDV = ironDV;
        this.servingWeightGrams = servingWeightGrams;

    }

}
