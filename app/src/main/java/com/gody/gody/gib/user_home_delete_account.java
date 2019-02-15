package com.gody.gody.gib;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class user_home_delete_account extends AppCompatActivity implements View.OnClickListener
{
    private Database db = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_delete_account);
        textStyles();
        setClickListeners();
    }

    private void setClickListeners()
    {
        {
            findViewById(R.id.customer_delete_account_button).setOnClickListener(this);
        }
    }

    private void textStyles()
    {
        // Find Elements
        TextView welcome_to_textview    = (TextView)findViewById(R.id.home_sib);
        Button changeNameButton         = (Button)findViewById(R.id.customer_delete_account_button);

        // Find fonts
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");

        // Set fonts
        welcome_to_textview.setTypeface(face);
        changeNameButton.setTypeface(face);
    }

    @Override
    public void onClick(View v)
    {
        final int registrationNo = Integer.parseInt(getIntent().getStringExtra("registrationNo"));

        String title    = "DELETE ACCOUNT";
        String message  = "Are you sure you want to delete account?\n*" +
                "This action cannot be undone*";
        String yes      = "YES";
        String no       = "NO";

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                db.deleteUser(registrationNo);
                dialog.dismiss();
                Intent back = new Intent();
                setResult(Activity.RESULT_OK, back);
                finish();

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
             dialog.dismiss();
            }
        });
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#1e7888"));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("C91f37"));
        try
        {
            TextView titleTextView = (TextView) alertDialog.findViewById(R.id.message);
            Typeface lato          = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
            titleTextView.setTypeface(lato);
        }
        catch (NullPointerException e)
        {

        }

    }
    @Override
    public void onBackPressed()
    {
        db.close();
        finish();

    }
}
