package com.simple.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    String pincodeURl=null;
    EditText fullNameEditText,mobileEditText,genderEditText,EditTextDOB,Address1EditText,
    addres2EditText,pincodeEditText;
    Spinner genderSppiner;
    Button registeredBtn,pincodeBtn;
    ProgressBar progressBar;
    TextView DistrictTextView,StateTextView;
    Calendar mycalender=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullNameEditText=findViewById(R.id.editTextfName);
        mobileEditText=findViewById(R.id.editNumber);
        genderSppiner=findViewById(R.id.GenderSpinner);
        EditTextDOB=findViewById(R.id.editTextDOB);
        addres2EditText=findViewById(R.id.Line2Address);
        Address1EditText=findViewById(R.id.Line1Address);
        pincodeBtn=findViewById(R.id.pincodeBtn);
        DistrictTextView=findViewById(R.id.districttext);
        StateTextView=findViewById(R.id.statetextView);
        pincodeEditText=findViewById(R.id.pincode);
        progressBar=findViewById(R.id.progressbar);

      registeredBtn=findViewById(R.id.RegisteredBtn);
        registeredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditTextvaildation();
                
            }
        });

     pincodeBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
              checkpincode();
         }
     });



        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mycalender.set(Calendar.YEAR,year);
                    mycalender.set(Calendar.MONTH,month);
                    mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                    String format="MM/dd/yyyy";
                SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
                EditTextDOB.setText(sdf.format(mycalender.getTime()));
            }
        };

        EditTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this,dateSetListener,mycalender.get(Calendar.YEAR),
                        mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void checkpincode() {
        progressBar.setVisibility(View.VISIBLE);
        String pincode=pincodeEditText.getText().toString().trim();
        String Url="https://api.postalpincode.in/pincode/"+pincode;

        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++) {
                        JSONObject successObj = array.getJSONObject(i);
                        JSONArray postOfficeArray=successObj.getJSONArray("PostOffice");
                        for (int j=0;j<postOfficeArray.length();j++){
                            JSONObject pincodeDetails=postOfficeArray.getJSONObject(j);

                            String District=pincodeDetails.getString("District");
                            String State=pincodeDetails.getString("State");
                            progressBar.setVisibility(View.GONE);
                            DistrictTextView.setText(District);
                            StateTextView.setText(State);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

     }

    private void inputEditTextvaildation() {
      String fullname=fullNameEditText.getText().toString().trim();
      String mobile=mobileEditText.getText().toString().trim();
      String address_line1=Address1EditText.getText().toString().trim();
      String pincode=pincodeEditText.getText().toString().trim();

      if (fullname.isEmpty()){
          fullNameEditText.setError("Please enter full name");
          fullNameEditText.requestFocus();
      }
      else  if (mobile.isEmpty()){
            mobileEditText.setError("Please enter mobile number");
            mobileEditText.requestFocus();
        }
        else if (address_line1.isEmpty()){
            Address1EditText.setError("Please enter address");
            Address1EditText.requestFocus();
        }
      else if (pincode.isEmpty()){
          pincodeEditText.setError("Please enter pincode");
          pincodeEditText.requestFocus();
      }
      else{
          Intent intent = new Intent(getApplicationContext(), WheaterActivity.class);
          intent.putExtra(fullname, "Fullname");
          intent.putExtra(mobile, "Mobile");
          intent.putExtra(address_line1, "address1");
          startActivity(intent);

      }
            addres2EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (addres2EditText.getText().toString().trim().length() < 3) {
                            addres2EditText.setError("please enter addrees minimum 3 characters");
                            addres2EditText.requestFocus();
                        } else {
                            addres2EditText.setError(null);
                        }
                    }
                }
            });


    }




}