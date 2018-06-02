package com.macrohard.cooklit.model.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.macrohard.cooklit.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//TODO::Needs to be given a look at

public class RecipeDetailActivity extends AppCompatActivity {

    ImageView titleImage;
    TextView title;
    ScrollView scrollView;
    TextView ingredientsText;
    Button gotoRecipeButton;
    JSONObject mJSONObject;
    Handler mHandler;
    String imageUri,titletxt,ingredients,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        titleImage = findViewById(R.id.recipeImage);
        title = findViewById(R.id.recipeTitle);
        scrollView = findViewById(R.id.scrollView);
        ingredientsText = findViewById(R.id.recipeDetailTextview);
        gotoRecipeButton = findViewById(R.id.gotoRecipe);
        mHandler = new Handler();
        new Thread(mMessageSender).start();
        ingredients = "";
        ingredientsText.setText("Loading...");

        gotoRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(RecipeDetailActivity.this, RecipeActivity.class);
                i2.putExtra("uri",link);
                startActivity(i2);
            }
        });

    }
    private Handler messageHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //progressDialog.dismiss();
            //ingredientsText.setText();
            if(msg.what == 0){
                ingredientsText.setText(mJSONObject.toString());
                title.setText(titletxt);
                ingredientsText.setText(ingredients);
                Picasso.with(RecipeDetailActivity.this).load(imageUri).into(titleImage);
            }
            else{
                ingredientsText.setText("error");
            }
        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
            request("https://api.edamam.com/search?q=chicken%20potato&app_id=30a51b67&app_key=4fac35f9506d8806f8cda87646dca06e");

            while(mJSONObject == null){

            }
            try{
                imageUri = mJSONObject.getJSONArray("hits").getJSONObject(0).getJSONObject("Recipe").getString("image");
                titletxt = mJSONObject.getJSONArray("hits").getJSONObject(0).getJSONObject("Recipe").getString("label");
                for(int i = 0; i < mJSONObject.getJSONArray("hits").getJSONObject(0).
                        getJSONObject("Recipe").getJSONArray("ingredientLines").length();++i){

                    ingredients += mJSONObject.getJSONArray("hits").getJSONObject(0).
                            getJSONObject("Recipe").getJSONArray("ingredientLines").getString(i) + "\n\n";
                    link = mJSONObject.getJSONArray("hits").getJSONObject(0).getJSONObject("Recipe").getString("url");
                }
            }
            catch (Exception e){

            }
            messageHandler.sendEmptyMessage(0);
            //mHandler.notify();
        }
    };

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
