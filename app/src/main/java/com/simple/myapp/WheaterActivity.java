package com.simple.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class WheaterActivity extends AppCompatActivity {

    TextInputEditText citynameEditText;
    TextView textViewCentigrade,textViewFahenheit,textViewlon,textViewlat;
    Button submitBtn;
    ProgressBar progressBar1,progressBar2,progressBar3,progressBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheater);

        citynameEditText=findViewById(R.id.citynameEditText);
        textViewCentigrade=findViewById(R.id.centigrade);
        textViewFahenheit=findViewById(R.id.fehenheit);
        textViewlon=findViewById(R.id.Longitude);
        textViewlat=findViewById(R.id.Latitude);
        submitBtn=findViewById(R.id.submitBtn);
        progressBar1=findViewById(R.id.progressbar1);
        progressBar2=findViewById(R.id.progressbar2);
        progressBar3=findViewById(R.id.progressbar3);
        progressBar4=findViewById(R.id.progressbar4);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWheater();
            }
        });

    }


    private void getWheater() {
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar3.setVisibility(View.VISIBLE);
        progressBar4.setVisibility(View.VISIBLE);
        String city=citynameEditText.getText().toString().trim();
        String URl="https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307&q="+city+"&aqi=no";
        StringRequest request=new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONObject location_Obj=object.getJSONObject("location");

                    String lon=location_Obj.getString("lon");
                    String lat=location_Obj.getString("lat");


                    JSONObject temp_obj=object.getJSONObject("current");

                    String temp_c=temp_obj.getString("temp_c");
                    String temp_f=temp_obj.getString("temp_f");

                    progressBar1.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);
                    progressBar3.setVisibility(View.GONE);
                    progressBar4.setVisibility(View.GONE);

                    textViewlon.setText(lon);
                    textViewlat.setText(lat);
                    textViewCentigrade.setText(temp_c);
                    textViewFahenheit.setText(temp_f);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}