package com.macrohard.cooklit.model.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.support.adapters.RecipeListViewAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import android.support.design.widget.Snackbar;

public class RecipeResultListActivity extends AppCompatActivity {



    public ListView RecipeView1,RecipeView2;
    public String upperURI = "https://api.edamam.com/search?q=";
    public String lowerURI = "&app_id=30a51b67&app_key=4fac35f9506d8806f8cda87646dca06e&from=0&to=20";
    public JSONObject mJSONObject;
    public String query;
    public ArrayList<String> imageuris,linkToRecipes,urilinks,tags;
    public ArrayList<String> ingredients;
    public Handler mHandler;
    public ImageButton likeButton;
    public boolean vsig,asig,psig,lsig;
    public String vtag,atag,ptag,ltag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vtag = "";
        atag = "";
        ptag = "";
        ltag = "";
        setContentView(R.layout.activity_recipe_result);
        Intent intent = getIntent();
        vsig = intent.getBooleanExtra("v",false);
        asig = intent.getBooleanExtra("a",false);
        psig = intent.getBooleanExtra("p",false);
        lsig = intent.getBooleanExtra("l",false);
        if(vsig){
            vtag = "&health=vegetarian";
        }
        if(asig){
            atag = "&health=alcohol-free";
        }
        if(psig){
            ptag = "&health=peanut-free";
        }
        if(lsig){
            ltag = "&calories=0-1000";
        }

        Log.d("vsig",vsig+"");
        Log.d("asig",asig+"");
        Log.d("psig",psig+"");
        Log.d("lsig",lsig+"");

        String [] ings = intent.getStringArrayExtra("ingredients");
        query = ings[0];
        //Log.d("ing list are",intent.getStringArrayExtra("ingredients")[1]);
        for(int i = 1; i < ings.length;++i){
            query += "%20";
            query +=ings[i];
        }
        query = upperURI+query+lowerURI+vtag+atag+ptag+ltag;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Recipes");
        Log.d("query is",query);
        mHandler = new Handler();
        RecipeView1 = findViewById(R.id.listView1);

        new Thread(mMessageSender).start();
        imageuris = new ArrayList<>();
        urilinks = new ArrayList<>();
        linkToRecipes = new ArrayList<>();
        ingredients = new ArrayList<>();
        tags = new ArrayList<>();

    }
    private Handler messageHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Log.d("handler working","1");
                if(imageuris.size() == 0){
                    Snackbar.make(RecipeView1, "Sorry We do not have any recipes for your input please retry", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                RecipeListViewAdapter recipeAdapter = new RecipeListViewAdapter(RecipeResultListActivity.this,R.layout.elementview,imageuris,urilinks,tags);
                RecipeView1.setAdapter(recipeAdapter);
                RecipeView1.setItemsCanFocus(false);
                RecipeView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(isNetworkAvailable() == true){

                            Intent i2 = new Intent(RecipeResultListActivity.this, RecipeDetailActivity.class);
                            i2.putExtra("uri",linkToRecipes.get(i));
                            i2.putExtra("imageuri",imageuris.get(i));
                            i2.putExtra("title",urilinks.get(i));
                            i2.putExtra("ingredients",ingredients.get(i));
                            i2.putExtra("tags",tags.get(i));
                            startActivity(i2);
                        }
                        else{
                            Snackbar.make(view, "Internet is not available, please retry", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    }
                });
            }

        }
    };

    private final Runnable mMessageSender = new Runnable() {
        public void run() {
                request(query);
                while(mJSONObject == null){

            }
            try{
                Log.d("mJsonObject is",mJSONObject.toString());
                for(int i = 0; i < (mJSONObject.getJSONArray("hits").length());++i){
                    Log.d("imageuris",mJSONObject.getJSONArray("hits").getJSONObject(i).getJSONObject("recipe").getString("image"));
                    imageuris.add(mJSONObject.getJSONArray("hits").getJSONObject(i).getJSONObject("recipe").getString("image"));
                    urilinks.add(mJSONObject.getJSONArray("hits").getJSONObject(i).getJSONObject("recipe").getString("label"));
                    linkToRecipes.add(mJSONObject.getJSONArray("hits").getJSONObject(i).getJSONObject("recipe").getString("url"));

                    String tagstring = "";
                    for(int ii = 0; ii < (mJSONObject.getJSONArray("hits").
                            getJSONObject(i).getJSONObject("recipe").getJSONArray("dietLabels").length());++ii){

                        tagstring += "#"+(mJSONObject.getJSONArray("hits").
                                getJSONObject(i).getJSONObject("recipe").getJSONArray("dietLabels").getString(ii) + " ");


                    }
                    tags.add(tagstring);
                    String ings = "";
                    for(int ii = 0; ii < (mJSONObject.getJSONArray("hits").
                            getJSONObject(i).getJSONObject("recipe").getJSONArray("ingredientLines").length());++ii){

                        ings += (mJSONObject.getJSONArray("hits").
                                getJSONObject(i).getJSONObject("recipe").getJSONArray("ingredientLines").getString(ii) + "\n\n");


                    }
                    ingredients.add(ings);
                }


            }
            catch (Exception e){

            }
            messageHandler.sendEmptyMessage(0);
        }
    };

    public void request(String type){

        OkHttpClient client = new OkHttpClient();
        Log.d("requesting",type);
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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
