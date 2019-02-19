package com.gody.gody.gib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class socialSignup extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_signup);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUp:
                Intent myIntent = new Intent(this, user_register.class);
                startActivityForResult(myIntent, 1);
                break;
            case R.id.Sign_Up_with_Gmail:
                Intent myIntent2 = new Intent(this, gmail.class);
                startActivityForResult(myIntent2, 2);
                break;
            case R.id.Sign_Up_with_Facebook:
                Intent myIntent3 = new Intent(this, facebook.class);
                startActivityForResult(myIntent3, 3);
                break;

        }
    }
}
