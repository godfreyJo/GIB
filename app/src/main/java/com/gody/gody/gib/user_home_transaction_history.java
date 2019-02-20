package com.gody.gody.gib;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Transaction History class
 * Lists Users transaction
 * **/

public class user_home_transaction_history extends AppCompatActivity
{
    private  Database db = new Database(this);

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_transaction_history);
        createListView();
        textStyles();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createListView()
    {
        final ListView listView = findViewById(R.id.list); // create ListView

        int registrationNo = Integer.parseInt(getIntent().getStringExtra("registrationNo")); //Get registration sent from parent

        final Customer user = db.selectCustomer(registrationNo); //get current user

        final List<Transaction> transactionList = db.allTransactions(registrationNo); //get all transactions from the Database
        transactionList.add(0, new Transaction());

        if (transactionList.size()==0) //if there are no transactions
        {
            ListAdapter customerAdapter = new UserTransactionAdapter(this, R.layout.customerlistrow, transactionList);
            listView.setAdapter(customerAdapter);
        }
        else
            {
                try
                {
                    ListAdapter customerAdapter = new UserTransactionAdapter(this, R.layout.customertransactionrow, transactionList);
                    listView.setAdapter(customerAdapter);

                }
                catch (Exception e)
                {
                    customToast(e.toString());
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try
                        {
                            if(position !=0)
                            {
                                String popupText;
                                Date date = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.ENGLISH).parse(transactionList.get(position).getDate());

                                popupText = "FROM: \t " + user.getName() + "\nTo: \t\t\t" +  db.selectCustomer(transactionList.get(position).getReceiver()).getName()+
                                "\nAmount: \tKES" + Double.toString(transactionList.get(position).getAmount()) + "\n Date: \t\t" + date.toString();

                                String title = "Transaction";
                                String ok = "OK";

                                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create(); //Inform user of login credentials
                                alertDialog.setTitle(title);
                                alertDialog.setMessage(popupText);
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, ok, new DialogInterface.OnClickListener()
                                    {

                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.dismiss();

                                    }
                                    });
                                alertDialog.show();
                                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1e7888"));

                                try  //If exception happens alertDialog will still work
                                {
                                    TextView messageTextView    = (TextView) alertDialog.findViewById(android.R.id.message);
                                    Typeface lato               = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
                                    messageTextView.setTypeface(lato);

                                }
                                catch (NullPointerException e)
                                {}

                            }
                        }
                        catch(Exception e)
                        {
                            customToast("unable to load Transaction details");
                        }
                    }

                });

            }
    }
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
    private void textStyles()
    {
        // Find Elements
        TextView welcome_to_textview    = findViewById(R.id.home_sib);

        // Find fonts
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");

        // Set fonts
        welcome_to_textview.setTypeface(face);
    }
}
