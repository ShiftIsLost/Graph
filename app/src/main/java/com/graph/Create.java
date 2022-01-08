package com.graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity {
    MaterialButton create;
    MaterialButton back;
    RequestQueue requestQueue;
    TextView id;
    TextView name;
    TextView type;
    TextView serial;
    String url = "http://213.143.69.216/api/v1/sensor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        create = (MaterialButton) findViewById(R.id.addbtn);
        back = (MaterialButton) findViewById(R.id.backbtn);
        name = (EditText) findViewById(R.id.getime);
        id = (EditText) findViewById(R.id.getid);
        type = (EditText) findViewById(R.id.gettype);
        serial = (EditText) findViewById(R.id.getserial);
        requestQueue = Volley.newRequestQueue(getApplicationContext());







        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = findViewById(R.id.getid);
                name = findViewById(R.id.getime);
                type = findViewById(R.id.gettype);
                serial = findViewById(R.id.getserial);
                addSensor(v);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }

    public void openActivity(){
        Intent intent = new Intent(this, Senzor.class);
        startActivity(intent);
    }

    public void addSensor(View view){
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("sensorName", name.getText());
            jsonBody.put("type", type.getText());
            jsonBody.put("serialNumber", serial.getText());
            jsonBody.put("firmwareVersion", "1");
            jsonBody.put("sensorId", id.getText());

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    Toast.makeText(Create.this,"failed", Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                public Map<String,String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ApiKey", "SecretKey");
                    params.put("Content-Type", "application/json");
                    return params;
                }

            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}