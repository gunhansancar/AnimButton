package com.gunhansancar.android.animbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Günhan on 17.08.2015.
 */
public class MainActivity extends AppCompatActivity {

    private AnimButton animButton;
    private EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animButton = (AnimButton) findViewById(R.id.animButton);
        txtInput = (EditText) findViewById(R.id.txtInput);

        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                animButton.goToState(text.length() == 0 ? AnimButton.FIRST_STATE : AnimButton.SECOND_STATE);
            }
        });
    }
}
