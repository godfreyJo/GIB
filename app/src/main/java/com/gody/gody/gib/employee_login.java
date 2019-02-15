package com.gody.gody.gib;

import android.app.Activity;
import android.content.Intent;
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

public class employee_login extends AppCompatActivity implements View.OnClickListener
{
    private Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        textStyles();
        setClickListeners();
    }





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v)
    {
        EditText employeeNoText = (EditText)findViewById(R.id.employee_login_number);
        EditText passwordText   = (EditText)findViewById(R.id.employee_login_password);

        String employeNoString  = employeeNoText.getText().toString().toString();
        String passwordString   = passwordText.getText().toString();

        if(employeNoString.length()==0||passwordString.length()==0)
        {
            customToast("Please enter in your credentials!!");
        }
        else
            {
                try
                {
                    int employeeNo  = Integer.parseInt(employeNoString);
                    try {
                        Employee employee = db.selectEmployee(employeeNo);

                        if(!BCrypt.checkpw(passwordString, employee.getPassword()))
                        {
                            customToast("Incorrect login details");
                        }
                        else
                            {
                                Intent back = new Intent();
                                back.putExtra("employeeNo", Integer.toString(employee.getEmployeeNo()));
                                setResult(Activity.RESULT_OK, back);
                                finish();

                        }
                    }
                    catch (Exception e)
                    {
                        customToast("Incorrect login details");
                    }
                }
                catch (Exception e)
                {
                    customToast("Incorrect login details");
                }
            }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void customToast(String text)
    {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundResource(R.drawable.customtoast);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setPadding(50, 20, 50, 20);
        TextView toastText = view.getRootView().findViewById(android.R.id.message);
        Typeface lato = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        toastText.setTypeface(lato);
        toast.show();

    }
    @Override
    public void onBackPressed()
    {
        db.close();
        finish();
    }
    private  void textStyles()
    {
        //get the elements

        TextView sib_textview     = (TextView)findViewById(R.id.home_sib);
        Button login              =  (Button)findViewById(R.id.employee_login);
        EditText employeeNo       = (EditText)findViewById(R.id.employee_login_number);
        EditText employeePassword = (EditText)findViewById(R.id.employee_login_password);

        //get fonts

        Typeface caviarDreams = Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");
        Typeface lato = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");

        //set font

        sib_textview.setTypeface(caviarDreams);
        login.setTypeface(caviarDreams);
        employeeNo.setTypeface(lato);
        employeePassword.setTypeface(lato);


    }
    public void  setClickListeners()
    {
        findViewById(R.id.employee_login).setOnClickListener(this);
    }
}
