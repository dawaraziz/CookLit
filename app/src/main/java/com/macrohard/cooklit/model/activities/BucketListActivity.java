package com.macrohard.cooklit.model.activities;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
public class BucketListActivity extends AppCompatActivity {
    public static final String NAME = "com.macrohard.cooklit.NAME";
    public static final String QUANTITY = "com.macrohard.cooklit.QUANTITY";
    public static final String DATE = "com.macrohard.cooklit.DATE";
    private int year;
    private int month;
    private int day;
    Intent intent = new Intent();
    Bundle extras = new Bundle();
    Integer idcounter = 1;
    HashMap<String, String> itemMap=new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucketlist);
        final EditText itemTxt = (EditText) findViewById(R.id.itemToAdd);
        final Button btn = (Button) findViewById(R.id.Add);
        final Button btn2 = (Button) findViewById(R.id.Submit);
        final HashMap<String, CheckBox> checkBoxes = new HashMap<>();
        final ArrayList<String> items = new ArrayList<>();
        final ArrayList<String> arrayList = new ArrayList<>();

        final TableLayout tl = (TableLayout) findViewById(R.id.innertable);

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
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                tr.setLayoutParams(lp);


                CheckBox cb = new CheckBox(BucketListActivity.this);
                checkBoxes.put(name, cb);

                TextView item = new TextView(BucketListActivity.this);
                item.setText(name);
                item.setTextSize(25);
//                item.setLayoutParams(new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.FILL_PARENT,
//                        RelativeLayout.LayoutParams.WRAP_CONTENT));
//                RelativeLayout.LayoutParams tlp = (RelativeLayout.LayoutParams) item.getLayoutParams();
//                tlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//                item.setLayoutParams(tlp);
//                final Calendar c = Calendar.getInstance();
//                year = c.get(Calendar.YEAR);
//                month = c.get(Calendar.MONTH);
//                day = c.get(Calendar.DAY_OF_MONTH);
//                String stryear = String.valueOf(year);
//                String strmonth = String.valueOf(month);
//                String strday = String.valueOf(day);
//                String date = stryear + "-" + strmonth + "-" +strday;
//                intent.putExtra("name" + String.valueOf(idcounter),name);
//                intent.putExtra("date" + String.valueOf(idcounter),date);
//                idcounter += 1;

                tr.addView(cb);
                tr.addView(item);
                tl.addView(tr);
//
                itemTxt.setText("");
//                setResult(RESULT_OK, intent);
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
                        String stryear = String.valueOf(year);
                        String strmonth = String.valueOf(month);
                        String strday = String.valueOf(day);
                        String date = stryear + "-" + strmonth + "-" +strday;
                        intent.putExtra("name" + String.valueOf(idcounter),name);
                        intent.putExtra("date" + String.valueOf(idcounter),date);
                        idcounter += 1;
                        setResult(RESULT_OK, intent);
                    }
                }
                finish();
            }
        });
    }
}