package com.example.cooklit.support;

import org.json.JSONObject;

/**
 * Created by qianwu on 2018-05-21.
 */

public class food {
    public String name;
    String brandName;
    String item_description;
    public int calories;
    int calories_from_fat;
    int total_fat;
    int saturated_fat;
    int trans_fatty_acid;
    int cholesterol;
    int sodium;
    int total_carbohydrate;
    int dietary_fiber;
    int sugars;
    int protein;
    int vitamin_a_dv;
    int vitamin_c_dv;
    int calcium_dv;
    int iron_dv;
    int serving_weight_grams;
    public food(){

    }
    /*public int retriveInt(int i){
        if(i == null){

        }
    }*/
    public food(JSONObject object){
        try{
        this.name = object.getString("item_name");
        this.brandName = object.getString("brand_name");;
        /*this.item_description = object.getString("item_description");;
        this.calories = object.getInt("nf_calories");;
        this.calories_from_fat = object.getInt("nf_calories_from_fat");
        this.total_fat = object.getInt("nf_total_fat");
        this.saturated_fat = object.getInt("nf_saturated_fat");
        this.trans_fatty_acid = object.getInt("nf_trans_fatty_acid");
        this.cholesterol = object.getInt("nf_cholesterol");
        this.sodium = object.getInt("nf_sodium");
        this.total_carbohydrate = object.getInt("nf_total_carbohydrate");
        this.dietary_fiber = object.getInt("nf_dietary_fiber");
        this.sugars = object.getInt("nf_sugars");
        this.protein = object.getInt("nf_protein");
        this.vitamin_a_dv = object.getInt("nf_vitamin_a_dv");
        this.vitamin_c_dv = object.getInt("nf_vitamin_c_dv");
        this.calcium_dv = object.getInt("nf_calcium_dv");
        this.iron_dv = object.getInt("nf_iron_dv");
        this.serving_weight_grams = object.getInt("nf_serving_weight_grams");*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*@Override
    public String toString() {
        return super.toString();
        return ""+name+" "+brandName+" "+item_description+" "+calories+ " " + calories_from_fat+ " "
                +total_fat+" " + saturated_fat+ " "+ trans_fatty_acid+ " "+ cholesterol+ " "+ sodium
                + total_carbohydrate+ " "+dietary_fiber+ " " + sugars+ " "+ protein+ " " + vitamin_a_dv+ " "+
    }*/

    public food(String name, String brandName, String item_description, int calories,
                int calories_from_fat, int total_fat, int saturated_fat, int trans_fatty_acid,
                int cholesterol, int sodium, int total_carbohydrate, int dietary_fiber, int sugars,
                int protein, int vitamin_a_dv, int vitamin_c_dv, int calcium_dv, int iron_dv,
                int serving_weight_grams) {
        this.name = name;
        this.brandName = brandName;
        this.item_description = item_description;
        this.calories = calories;
        this.calories_from_fat = calories_from_fat;
        this.total_fat = total_fat;
        this.saturated_fat = saturated_fat;
        this.trans_fatty_acid = trans_fatty_acid;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.total_carbohydrate = total_carbohydrate;
        this.dietary_fiber = dietary_fiber;
        this.sugars = sugars;
        this.protein = protein;
        this.vitamin_a_dv = vitamin_a_dv;
        this.vitamin_c_dv = vitamin_c_dv;
        this.calcium_dv = calcium_dv;
        this.iron_dv = iron_dv;
        this.serving_weight_grams = serving_weight_grams;
    }

    void update(String name, String brandName, String item_description, int calories,
                int calories_from_fat, int total_fat, int saturated_fat, int trans_fatty_acid,
                int cholesterol, int sodium, int total_carbohydrate, int dietary_fiber, int sugars,
                int protein, int vitamin_a_dv, int vitamin_c_dv, int calcium_dv, int iron_dv,
                int serving_weight_grams){
        this.name = name;
        this.brandName = brandName;
        this.item_description = item_description;
        this.calories = calories;
        this.calories_from_fat = calories_from_fat;
        this.total_fat = total_fat;
        this.saturated_fat = saturated_fat;
        this.trans_fatty_acid = trans_fatty_acid;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.total_carbohydrate = total_carbohydrate;
        this.dietary_fiber = dietary_fiber;
        this.sugars = sugars;
        this.protein = protein;
        this.vitamin_a_dv = vitamin_a_dv;
        this.vitamin_c_dv = vitamin_c_dv;
        this.calcium_dv = calcium_dv;
        this.iron_dv = iron_dv;
        this.serving_weight_grams = serving_weight_grams;

    }

}
