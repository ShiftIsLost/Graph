package com.graph;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.material.button.MaterialButton;



public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String url = "http://213.143.69.216/api/v1/applicationuser";

    TextView uporabnik ;

    MaterialButton vpis ;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        vpis = (MaterialButton) findViewById(R.id.vpisbtn);


        vpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uporabnik = findViewById(R.id.uporabnik);
                //Toast.makeText(MainActivity.this,"test vpis", Toast.LENGTH_SHORT).show();
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String username = "";
                        Toast.makeText(MainActivity.this,"Logging in", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                username = object.getString("userName");
                                uid = object.getString("id");
                            } catch (JSONException e){
                                e.printStackTrace();
                                return;
                            }
                            if(uporabnik.getText().toString().equals(username)){
                                openActivity();
                            }
                        }

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
    }
    public void openActivity(){
        Intent intent = new Intent(this, Senzor.class);
        ((MyApplication) this.getApplication()).setuser(uid);
        startActivity(intent);
    }

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };




}