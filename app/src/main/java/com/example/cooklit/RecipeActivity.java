package com.example.cooklit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RecipeActivity extends AppCompatActivity {



    public WebView webview;
    //public ImageView imageView;
    private ProgressDialog progressBar;
    private static final String TAG = "Webview loading";
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        webview = (WebView) findViewById(R.id.webview);
        //imageView = (ImageView) findViewById(R.id.imageView);
        uri = getIntent().getStringExtra("uri");
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        Log.d("new webview",uri);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
            }

        });
        webview.loadUrl(uri);
        //Picasso.with(RecipeActivity.this).load("http://static.food2fork.com/StuffedChickenBreastSquare63bd.jpg").into(imageView);
    }
}
/*Intent i2 = null;
                i2 = new Intent(FridgeActivity.this, RecipeActivity.class);
                i2.putExtra("uri","http://www.bbcgoodfood.com/recipes/2080/chicken-goats-cheese-and-cherry-tomato-bake");
                //i2.putExtra("title",mProvider.getNames().get(i));
                startActivity(i2);*/