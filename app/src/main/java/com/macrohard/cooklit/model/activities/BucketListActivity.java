package com.macrohard.cooklit.model.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.macrohard.cooklit.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
public class BucketListActivity extends AppCompatActivity {
    private int year;
    private int month;
    private int day;
    Intent intent = new Intent();
    Integer idcounter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucketlist);
        final EditText itemTxt = (EditText) findViewById(R.id.itemToAdd);
        final Button btn = (Button) findViewById(R.id.Add);
        final Button btn2 = (Button) findViewById(R.id.Submit);
        final Button delete = (Button) findViewById(R.id.Delete);
        final HashMap<String, CheckBox> checkBoxes = new HashMap<>();
        final ArrayList<String> items = getItems(this, "1");

        final TableLayout tl = (TableLayout) findViewById(R.id.innertable);

        if (items.size() != 0) {
            createView(tl, checkBoxes, items);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> newItems = new ArrayList<>();
                for (String name: items) {
                    if (!checkBoxes.get(name).isChecked()) {
                        newItems.add(name);
                    }
                }
                for (String name: newItems) {
                    if (!items.contains(name)) {
                        checkBoxes.remove(name);
                    }
                }
                items.clear();
                tl.removeAllViews();
                for (String name: newItems) {
                    items.add(name);
                }
                createView(tl, checkBoxes, items);
                setItemsPersistence(BucketListActivity.this, "1", items);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = itemTxt.getText().toString();
                items.add(name);

                TableRow tr = new TableRow(BucketListActivity.this);
                tr.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tr.getLayoutParams();
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                tr.setLayoutParams(lp);


                CheckBox cb = new CheckBox(BucketListActivity.this);
                cb.setHeight(120);
                cb.setWidth(120);
                checkBoxes.put(name, cb);

                TextView item = new TextView(BucketListActivity.this);
                item.setText(name);
                item.setTextSize(40);

                tr.addView(cb);
                tr.addView(item);
                tl.addView(tr);

                itemTxt.setText("");
                setItemsPersistence(BucketListActivity.this, "1", items);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String name: items) {
                    if (checkBoxes.get(name).isChecked()) {
                        final Calendar c = Calendar.getInstance();
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        day = c.get(Calendar.DAY_OF_MONTH);
                        String d = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                        String stryear = String.valueOf(year);
                        String strmonth = String.valueOf(month);
                        String strday = String.valueOf(day);
                        String date = stryear + "-" + strmonth + "-" +strday;
                        intent.putExtra("name" + String.valueOf(idcounter),name);
                        intent.putExtra("date" + String.valueOf(idcounter),d);
                        idcounter += 1;
                        setResult(RESULT_OK, intent);
                    }
                }
                setItemsPersistence(BucketListActivity.this, "1", items);
                finish();
            }
        });
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//
//    }

    public static void setItemsPersistence(Context context, String key, ArrayList<String> names) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray arr = new JSONArray();
        for (int i = 0; i < names.size(); i++) {
            arr.put(names.get(i));
        }
        if (!names.isEmpty()) {
            editor.putString(key, arr.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }

    public void createView(TableLayout tl, HashMap<String, CheckBox> checkBoxes, ArrayList<String> items) {
        for (int i = 0; i < items.size(); i++) {
            CheckBox cb = new CheckBox(BucketListActivity.this);
            cb.setHeight(120);
            cb.setWidth(120);

            checkBoxes.put(items.get(i), cb);
            checkBoxes.put(items.get(i), cb);
            TableRow tr = new TableRow(BucketListActivity.this);
            tr.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tr.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.RIGHT_OF);
            tr.setLayoutParams(lp);

            TextView item = new TextView(BucketListActivity.this);
            item.setText(items.get(i));
            item.setTextSize(40);

            tr.addView(cb);
            tr.addView(item);
            tl.addView(tr);
        }
    }

    public static ArrayList<String> getItems(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> names = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray arr = new JSONArray(json);
                for (int i = 0; i < arr.length(); i++) {
                    String name = arr.optString(i);
                    names.add(name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return names;
    }
}