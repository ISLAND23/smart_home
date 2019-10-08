package com.example.hp.smart_home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class AdminActivity extends AppCompatActivity {

    TextView tx_temp, tx_hum, tx_body, tx_gas;
    Button chuanglian_open, chuanglian_close;
    ToggleButton tb;
    Switch swit;
    RequestQueue rq;
    String temp, hum, body, gas;
    private final static int SensorData = 1;
    int value;
    int num = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_use);
        tx_temp = findViewById(R.id.get_temp);
        tx_hum = findViewById(R.id.get_hum);
        tx_body = findViewById(R.id.get_body);
        tx_gas = findViewById(R.id.get_gas);

        chuanglian_open = findViewById(R.id.chuanlian_open);
        chuanglian_close = findViewById(R.id.chuanlian_close);


        tb = findViewById(R.id.tog_btn);
        swit = findViewById(R.id.swch);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        rq = Volley.newRequestQueue(this);
        if (num == 1) {
            auto();
        }
        GetSensorData();

        chuanglian_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_open();


            }
        });

        chuanglian_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_close();


            }
        });

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  if(b) {    //切换进入手动模式
                      light_off();
                      Toast.makeText(AdminActivity.this, "it's on now", Toast.LENGTH_SHORT).show();
                      swit.setVisibility(View.VISIBLE);
                  }else{     //切换进入自动模式
                      light_off();
                      swit.setChecked(false);

                      Toast.makeText(AdminActivity.this, "it's off now", Toast.LENGTH_SHORT).show();
                      swit.setVisibility(View.INVISIBLE);

                      TimerTask task = new TimerTask() {
                          @Override
                          public void run() {
                              auto();
                          }
                      };
                      Timer timer = new Timer();
                      timer.schedule(task,2000);
                  }
              }
          }

        );
        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    light_on();
                }else{
                    light_off();
                }
            }
        });
    }
    public void cl_open() {
        String url_1 = "http://39.97.189.133:8080/Web/Servlet/SetCtlServlet?a_type=motor&a_status=CW";
        StringRequest sr = new StringRequest(Request.Method.GET, url_1, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        rq.add(sr);
    }

    public void cl_close() {
        String url_2 = "http://39.97.189.133:8080/Web/Servlet/SetCtlServlet?a_type=motor&a_status=CCW";
        StringRequest sr = new StringRequest(Request.Method.GET, url_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        rq.add(sr);
    }

    public void light_on() {
        String url_3 = "http://39.97.189.133:8080/Web/Servlet/SetCtlServlet?a_type=jdq&a_status=manucl";
        Log.v("url","======"+url_3);
        StringRequest sr = new StringRequest(Request.Method.GET, url_3, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        rq.add(sr);
    }

    public void light_off() {
        String url_4 = "http://39.97.189.133:8080/Web/Servlet/SetCtlServlet?a_type=jdq&a_status=manuop";
        StringRequest sr = new StringRequest(Request.Method.GET, url_4, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        rq.add(sr);
    }

    public void auto() {
        String url_5 = "http://39.97.189.133:8080/Web/Servlet/SetCtlServlet?a_type=jdq&a_status=auto";
        StringRequest sr = new StringRequest(Request.Method.GET, url_5, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        rq.add(sr);
    }

    public void notification() {
        Intent intent = new Intent(getBaseContext(), AdminActivity.class);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pi = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification no = new Notification.Builder(getBaseContext())
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("This is a Notification")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("A nofication from smart_home ")
                .setContentText("可燃气体出现异常")
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        nm.notify(1, no);
    }
    public void GetSensorData() {
        Thread getData = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://39.97.189.133:8080/Web/Servlet/GetSensorDataServlet";
                    while(!Thread.interrupted()) {
                        JsonObjectRequest jobjr = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    temp = jsonObject.get("temp").toString();
                                    hum = jsonObject.get("hum").toString();
                                    body = jsonObject.get("body").toString();
                                    gas = jsonObject.get("gas").toString();
                                    Message msg = Message.obtain();
                                    msg.what = SensorData;
                                    handler.sendMessage(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e("ERROR", volleyError.getMessage());
                            }
                        }

                        );
                        rq.add(jobjr);

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
        getData.start();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SensorData) {
                tx_temp.setText(temp + "C");
                tx_hum.setText(hum + "%");
                if (body.equals("0")) {
                    tx_body.setText("无人");
                    value = 0;
                } else {
                    tx_body.setText("有人");
                    value = 1;
                }
                if (gas.equals("0")) {
                    tx_gas.setText("正常");

                } else {
                    tx_gas.setText("异常");
                    notification();
                }
            }

        }


    };
}

