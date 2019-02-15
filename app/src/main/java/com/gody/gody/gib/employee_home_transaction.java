package com.gody.gody.gib;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//Allows employers to make transcation from a user account to another user account

public class employee_home_transaction extends AppCompatActivity implements View.OnClickListener {
    private Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_transaction);
        textStyles();
        setClickListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v)
    {
        try
        {
            EditText receiverEditText   = (EditText)findViewById(R.id.user_transaction_receiver);
            EditText senderEditText     = (EditText)findViewById(R.id.user_transaction_sender);
            EditText amountEditText     = (EditText)findViewById(R.id.user_transaction_amount);

            String receiverString       = receiverEditText.getText().toString();
            String senderString         = senderEditText.getText().toString();
            String amountString         = amountEditText.getText().toString();

            int senderNo                = Integer.parseInt(senderString);
            int receiverNo              = Integer.parseInt(receiverString);
            double amount               = Double.parseDouble(amountString);

            Customer sender     = db.selectCustomer(senderNo);
            Customer receiver   = db.selectCustomer(receiverNo);

            db.updateBalance(receiverNo, receiver.getBalance() + amount);               //Add amount to receiver balance
            db.updateBalance(senderNo,                                                          //Take away amount from sender balance
                    sender.getBalance() - amount);
            db.insertTransaction(senderNo, receiverNo, amount);


            String title    = "Your transaction has been completed.";
            String message  = "Receiver: " + receiver.getName() +
                    "\n" + "Amount: " + amountString;
            String ok       = "OK";

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();       //Inform user of login credentials
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, ok,
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1e7888"));

            try                                                                         //If exception happens alertDialog will still work
            {
                TextView messageTextView    = (TextView) alertDialog.findViewById(android.R.id.message);
                Typeface lato               =  Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
                messageTextView.setTypeface(lato);

                TextView titleTextView = (TextView) alertDialog.findViewById(R.id.alertTitle);
                titleTextView.setTypeface(lato);
            }
            catch (NullPointerException e)
            {}
        }
        catch (Exception e)
        {
            customToast("This transaction has failed.");
        }

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void customToast(String text)
    {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.customtoast);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setPadding(50, 20, 50, 20);
        TextView toastText      =  view.getRootView().findViewById(android.R.id.message);           //Find Toast TextView
        Typeface lato           =  Typeface.createFromAsset(getAssets(),                            //Get font
                "fonts/CaviarDreams.ttf");
        toastText.setTypeface(lato);                                                                //Set Toast TextView font
        toast.show();

    }
    @Override
    public void onBackPressed()
    {
        db.close();
        finish();
    }
    private void textStyles()                                                                   //Set fonts for Buttons and TextViews
    {
        // Find Elements
        TextView welcome_to_textview    = (TextView)findViewById(R.id.home_sib);
        Button transaction              = (Button)findViewById(R.id.user_transaction_submit);

        // Find fonts
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");

        // Set fonts
        welcome_to_textview.setTypeface(face);
        transaction.setTypeface(face);
    }
    public void setClickListeners()                                                             //Set on click listener
    {
        findViewById(R.id.user_transaction_submit).setOnClickListener(this);
    }
}
