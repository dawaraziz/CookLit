package com.example.cooklit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cooklit.R;

public class AddItem extends AppCompatActivity {

    public static final String Name = "com.example.cooklit.NAME";
    public static final String Quantity = "com.example.cooklit.QUANTITY";


    private EditText mEditNameView;
    private EditText mEditQuantityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        mEditNameView = findViewById(R.id.name);
        mEditQuantityView = findViewById(R.id.quantity);

        final Button add_button = findViewById(R.id.add);

        add_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNameView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditNameView.getText().toString();
                    String quantity = mEditQuantityView.getText().toString();
                    replyIntent.putExtra(Name, name);
                    replyIntent.putExtra(Quantity, quantity);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
