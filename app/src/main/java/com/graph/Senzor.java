package com.graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Senzor extends AppCompatActivity {
    String uid;
    TextView serijska;
    TextView izpis;
    MaterialButton iskanje;
    MaterialButton logout;
    MaterialButton newsenzor;
    String url = "http://213.143.69.216/api/v1/sensor";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzor);
        uid = ((MyApplication) this.getApplication()).getuser();
        //Toast.makeText(Senzor.this,uid, Toast.LENGTH_LONG).show();
        requestQueue = Volley.newRequestQueue(Senzor.this);

        iskanje = (MaterialButton) findViewById(R.id.senzbtn);
        logout = (MaterialButton) findViewById(R.id.logout);
        newsenzor = (MaterialButton) findViewById(R.id.newbtn);


        iskanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serijska = findViewById(R.id.senz);
                izpis = findViewById(R.id.izpis);
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String out = "";
                        String serialNumber="";
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                serialNumber = object.getString("serialNumber");
                                String name = object.getString("sensorName");
                                String type = object.getString("type");
                                String firmwareVersion = object.getString("firmwareVersion");
                                if(serijska.getText().toString().equals(serialNumber)){
                                    out = "Name: "+name+"\n\n"+"Type: "+type+"\n\n"+"FirmwareVersion: "+firmwareVersion;
                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                                return;
                            }
                        }
                        izpis.setText(out);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("REST error", error.getMessage());
                    }
                })
                {
                    @Override
                    public Map<String,String> getHeaders() throws AuthFailureError
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("ApiKey", "SecretKey");
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });
        newsenzor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });
        }
    public void openCreate(){
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
    }
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    }
