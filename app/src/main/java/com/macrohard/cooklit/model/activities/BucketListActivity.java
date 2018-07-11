package com.macrohard.cooklit.model.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
        final EditText qTxt = (EditText) findViewById(R.id.amount);
        final Button btn = (Button) findViewById(R.id.Add);
        final Button btn2 = (Button) findViewById(R.id.Submit);
        final ListView list = (ListView) findViewById(android.R.id.list);
        final ArrayList<String> arrayList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.add(itemTxt.getText().toString() + "  " + qTxt.getText().toString());
                String name = itemTxt.getText().toString();
                String quantity = qTxt.getText().toString();
                adapter.notifyDataSetChanged();
                itemTxt.setText("");
                qTxt.setText("");
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                String stryear = String.valueOf(year);
                String strmonth = String.valueOf(month);
                String strday = String.valueOf(day);
                String date = stryear + "-" + strmonth + "-" +strday;
                intent.putExtra("name" + String.valueOf(idcounter),name);
                intent.putExtra("quantity" + String.valueOf(idcounter),quantity);
                intent.putExtra("date" + String.valueOf(idcounter),date);
                idcounter += 1;
                setResult(RESULT_OK, intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}