package com.example.android.testqrcodereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    EditText edtName, edtPhone;
    Button sendButton;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edtName = (EditText) findViewById(R.id.nameField);
        edtPhone = (EditText) findViewById(R.id.phoneField);
        sendButton=(Button)findViewById(R.id.sendButton);
        queue = Volley.newRequestQueue(getApplicationContext());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().trim().length() > 0 && edtPhone.getText().toString().trim().length() > 0) {

                    postData(edtName.getText().toString().trim(), edtPhone.getText().toString().trim());

                } else {

                    Toast.makeText(Main2Activity.this, "Required fields are left empty",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void postData(final String name, final String phone) {


        StringRequest request = new StringRequest(

                Request.Method.POST,

                Constants.url,

                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {

                        Log.d("TAG", "Response: " + response);

                        if (response.length() > 0) {

                            Toast.makeText(Main2Activity.this,"Successfully posted!",Toast.LENGTH_SHORT).show();

                            edtName.setText(null);

                            edtPhone.setText(null);

                        } else {

                            Toast.makeText(Main2Activity.this,"Try again",Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {



            @Override

            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Main2Activity.this,"Error while posting data",Toast.LENGTH_SHORT).show();

            }

        }) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put(Constants.nameField, name);

                params.put(Constants.phoneField, phone);

                return params;

            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(

                50000,

                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }
}
