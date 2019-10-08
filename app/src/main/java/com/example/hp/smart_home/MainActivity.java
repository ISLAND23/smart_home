package com.example.hp.smart_home;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    EditText name, psw;
    Button login, cancle;
    String userString;
    String pswString;
    RequestQueue requestQueue;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        psw = (EditText) findViewById(R.id.psw);
        login = (Button) findViewById(R.id.login);
        cancle = (Button) findViewById(R.id.cancle);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = name.getText().toString();
                pswString = psw.getText().toString();
                login();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
    }

    public void login() {
        String url = "http://39.97.189.133:8080/Web/Servlet/UserLoginServlet?username=user&password=123456";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                if(userString.equals("user")&& pswString.equals("123456")) {
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this,"chenggong,",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this,"SHIBAI,",Toast.LENGTH_LONG).show();
            }


        });
        requestQueue.add(stringRequest);
    }
}