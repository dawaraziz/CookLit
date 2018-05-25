package com.example.cooklit.support;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qianwu on 2018-05-21.
 */


public class apiCall {
    public JSONObject mJSONObject;
    public apiCall(){

    }


    public void request(String type){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(type).build();

        okhttp3.Call call = client.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("FAIL","TRUE");

            }


            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    //Response response = call.execute();
                    if (response.isSuccessful()) {

                        try{
                            Log.d("jsonData is",jsonData);

                            mJSONObject = new JSONObject(jsonData);
                            /*String name = mJSONObject.getString("item_name");
                            String brandname = mJSONObject.getString("brand_name");
                            String describtion = mJSONObject.getString("item_description");

                            int calories = mJSONObject.getInt("nf_calories");
                            int calories_from_fat = mJSONObject.getInt("nf_calories_from_fat");
                            int total_fat = mJSONObject.getInt("nf_total_fat");
                            int saturated_fat = mJSONObject.getInt("nf_saturated_fat");
                            int trans_fatty_acid = mJSONObject.getInt("nf_trans_fatty_acid");
                            int cholesterol = mJSONObject.getInt("nf_cholesterol");
                            int sodium = mJSONObject.getInt("nf_sodium");
                            int total_carbohydrate = mJSONObject.getInt("nf_total_carbohydrate");
                            int dietary_fiber = mJSONObject.getInt("nf_dietary_fiber");
                            int sugars = mJSONObject.getInt("nf_sugars");
                            int protein = mJSONObject.getInt("nf_protein");
                            int vitamin_a_dv = mJSONObject.getInt("nf_vitamin_a_dv");
                            int vitamin_c_dv = mJSONObject.getInt("nf_vitamin_c_dv");
                            int calcium_dv = mJSONObject.getInt("nf_calcium_dv");
                            int iron_dv = mJSONObject.getInt("nf_iron_dv");
                            int serving_weight_grams = mJSONObject.getInt("nf_serving_weight_grams");

                            f.update(name,brandname,describtion,calories,calories_from_fat,total_fat,
                                    saturated_fat,trans_fatty_acid,cholesterol,sodium,total_carbohydrate,
                                    dietary_fiber,sugars,protein,vitamin_a_dv,vitamin_c_dv,calcium_dv,
                                    iron_dv,serving_weight_grams);*/



                        }

                        catch (Exception e){
                            Log.d("exception caught","1");
                        }


                    }
                    else{
                        Log.d("jsonData is","not successful");
                    }
                }
                catch (IOException e) {

                    Log.d("exception caught","2");
                }

            }
        });

    }
}
