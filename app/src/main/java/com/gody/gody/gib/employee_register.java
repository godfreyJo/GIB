package com.gody.gody.gib;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.SecureRandom;

public class employee_register extends AppCompatActivity implements View.OnClickListener {
    private  Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
        textStyles();
        setClickListeners();
    }

    private void setClickListeners()
    {
        findViewById(R.id.employee_register).setOnClickListener(this);
    }

    private void textStyles()
    {
        // Find Element
        TextView sib_textview   = (TextView)findViewById(R.id.home_sib);
        Button login            = (Button)findViewById(R.id.customer_register);
        EditText name           = (EditText)findViewById(R.id.user_register_name);

        // Find fonts
        Typeface caviarDreams   =  Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");
        Typeface lato           =  Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");

        // Set font
        sib_textview.setTypeface(caviarDreams);
        login.setTypeface(caviarDreams);
        name.setTypeface(lato);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        EditText nameText = findViewById(R.id.employee_register_name);
        String name = nameText.getText().toString();

        if (TextUtils.isEmpty(name)) {
            customToast("Please Enter your name.");
        } else {
            if (name.length() > 25) {
                customToast("this name is too long");
            } else {
                SecureRandom random = new SecureRandom();
                String PACString = Integer.toString(random.nextInt());
                PACString = PACString.substring(PACString.length() - 4); //generate cryptographically random number
                int PAC = Integer.parseInt(PACString);

                int e_registrationNo = db.insertEmployee(PAC, name, 0);

                String title = "Your Login credentials";
                String message = "RegistrationNo: " + e_registrationNo + "\n" +
                        "PAC:  " + PACString + ".\n\n Please note dow your login information. ";
                String login = "Login";

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent back = new Intent();
                        setResult(Activity.RESULT_OK, back);
                        db.close();
                        finish();
                    }
                });
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1e7888"));

                try {
                    TextView messageTextView = alertDialog.findViewById(android.R.id.message);
                    Typeface lato = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
                    messageTextView.setTypeface(lato);

                    TextView titleTextView = (TextView) alertDialog.findViewById(R.id.alertTitle);
                    titleTextView.setTypeface(lato);
                } catch (NullPointerException e) {
                }
            }

        }
    }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        private void customToast (String text)
        {
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.customtoast);
            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            view.setPadding(50, 20, 50, 20);
            TextView toastText = view.getRootView().findViewById(android.R.id.message);       //Find Toast TextView
            Typeface lato = Typeface.createFromAsset(getAssets(),                        //Get font
                    "fonts/CaviarDreams.ttf");
            toastText.setTypeface(lato);                                                            //Set Toast TextView font
            toast.show();
        }
        public void onBackPressed()
        {
            db.close();
            finish();
        }


}
