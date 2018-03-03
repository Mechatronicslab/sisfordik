package com.example.juli_soep.sekolah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.juli_soep.sekolah.BAK.Bak;
import com.example.juli_soep.sekolah.admin.AdminActivity;
import com.example.juli_soep.sekolah.guru.GuruActivity;
import com.example.juli_soep.sekolah.home.MainActivity;
import com.example.juli_soep.sekolah.siswa.SiswaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import volley.AppController;
import volley.Config_URL;
import volley.Session;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = LoginActivity.class.getSimpleName();
    EditText username, password;
    private ProgressDialog pDialog;
    private Session session;
    SharedPreferences prefs;
    Context context;
    String id, level, user;
    int socketTimeout = 15000; // 30 seconds. You can change it
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    Button btn_login;
    String nohp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        prefs = LoginActivity.this.getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        session = new Session(getApplicationContext());

        id = prefs.getString("id", "");
        level = prefs.getString("lvl", "");
        nohp = prefs.getString("nohp", "");
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            if (level.contains("admin")) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nohp",nohp);
                startActivity(intent);
            }
            else if (level.contains("guru")) {
                Intent intent = new Intent(LoginActivity.this, GuruActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nohp", nohp);
                startActivity(intent);

            }else if (level.contains("siswa")) {
                Intent intent = new Intent(LoginActivity.this, SiswaActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nohp", nohp);
                startActivity(intent);
            }else if (level.contains("bak")) {
                Intent intent = new Intent(LoginActivity.this, Bak.class);
                intent.putExtra("id", id);
                intent.putExtra("nohp", nohp);
                startActivity(intent);
            }


        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_login){
            String usernamenya = username.getText().toString();
            String passwordnya = password.getText().toString();

            //Check for empty data in the form
            if(usernamenya.trim().length() > 0 && passwordnya.trim().length() > 0){
                checkLogin(usernamenya, passwordnya);
            }else{
                //Prompt user to enter credential
                Toast.makeText(getApplicationContext(),
                        "Masukan Email atau Password Anda !!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkLogin(final String username, final String password){

        //Tag used to cancel the request
        String tag_string_req = "tag";

        pDialog.setMessage("Login, Please Wait.....");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config_URL.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if(!error) {
                        String getObject = jObj.getString("tag");
                        JSONArray jsonArray = new JSONArray(getObject);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            user = jsonObject.getString("username");
                            level = jsonObject.getString("lvl");
                            id = jsonObject.getString("id");
                            nohp = jsonObject.getString("no_hp");}
                        //user successsfully
                        //Create login Session
                        session.setLogin(true);

                        storeRegIdinSharedPref(LoginActivity.this, id, nohp, level);
                        String theRole = level;
                        if (theRole.equals("admin")) {
                            //Lauch to main activity
                            Intent i = new Intent(LoginActivity.this,
                                    AdminActivity.class);
                            i.putExtra("nohp",nohp);
                            i.putExtra("id", id);
                            i.putExtra("username", user);
                            i.putExtra("lvl", level);
                            startActivity(i);
                        } else if (theRole.equals("guru")) {
                            //Lauch to main activity
                            Intent i = new Intent(LoginActivity.this,
                                    GuruActivity.class);
                            i.putExtra("nohp",nohp);
                            i.putExtra("id", id);
                            i.putExtra("username", user);
                            i.putExtra("lvl", level);
                            startActivity(i);
                        } else if (theRole.equals("siswa")) {
                            //Lauch to main activity
                            Intent i = new Intent(LoginActivity.this,
                                    SiswaActivity.class);
                            i.putExtra("nohp",nohp);
                            i.putExtra("id", id);
                            i.putExtra("username", user);
                            i.putExtra("lvl", level);
                            startActivity(i);
                        } else if (theRole.equals("bak")) {
                            //Lauch to main activity
                            Intent i = new Intent(LoginActivity.this,
                                    Bak.class);
                            i.putExtra("nohp",nohp);
                            i.putExtra("id", id);
                            i.putExtra("username", user);
                            i.putExtra("lvl", level);
                            startActivity(i);
                        }

                    }else{
                        String error_msg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                error_msg, Toast.LENGTH_LONG).show();
                    }


                }catch (JSONException e){
                    //JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG, "Login Error : " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config_URL.TAG, Config_URL.TAG_LOGIN);
                params.put(Config_URL.username, username);
                params.put(Config_URL.password, password);

                return params;
            }
        };

        strReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void storeRegIdinSharedPref(Context context,String iduser,String nohp, String level) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", iduser);
        editor.putString("nohp", nohp);
        editor.putString("lvl", level);
        editor.commit();
    }

}
