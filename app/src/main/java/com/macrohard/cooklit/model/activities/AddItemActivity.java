package com.macrohard.cooklit.model.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.macrohard.cooklit.R;

public class AddItemActivity extends AppCompatActivity {

    public static final String NAME = "com.macrohard.cooklit.NAME";
    public static final String QUANTITY = "com.macrohard.cooklit.QUANTITY";

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
                    replyIntent.putExtra(NAME, name);
                    replyIntent.putExtra(QUANTITY, quantity);
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }

}
