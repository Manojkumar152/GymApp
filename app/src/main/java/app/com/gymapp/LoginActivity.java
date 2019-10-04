package app.com.gymapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import app.com.gymapp.ApiCalls.ApiInterface;
import app.com.gymapp.ApiCalls.RetrofitClient;
import app.com.gymapp.Models.Login;
import app.com.gymapp.Models.RegistrationDetails;
import app.com.gymapp.Util.DisplaySnackBar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtEmail, txtPassword;
    private SessionManager session;
    private Button submit;
    private ProgressDialog dialog;
    private String TAG = "LoginActivity";
    private LinearLayout mlinear_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        submit= findViewById(R.id.submit);
        mlinear_signout = findViewById(R.id.linear_signout);

        session = new SessionManager(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.wait));
        mlinear_signout.setOnClickListener(this);
    }

    public void signup_with_email(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterScreen.class);
        startActivity(i);
    }

    public void login(View view) {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        if (email.equals("") || password.equals(""))
            Toasty.info(this, getString(R.string.blank_error), Toast.LENGTH_SHORT).show();
        else
            loginUser(email,password);
    }

    private void loginUser(String email, String password) {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<Login> call= service.loginUser(email,password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.d(TAG,"Response"+response.body()+"Address"+ response.body().getResult().get(0).getImage());
                if(response.body().getStatus().equals("1")){
                    String name=response.body().getResult().get(0).getFirst_name()+ response.body().getResult().get(0).getLast_name();
                    String image=response.body().getResult().get(0).getImage();
                    String id= response.body().getResult().get(0).getId();
                    String address=response.body().getResult().get(0).getAddress();
                    session.createLoginSession(name, image, id, address);
                    if (session.isLoggedIn()) {
                        Toasty.success(getApplicationContext(),
                                "You have successfully logged in",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        LoginActivity.this.startActivity(i);
                    }
                }else{
                    Toasty.error(getApplicationContext(),response.body().getError());
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toasty.warning(LoginActivity.this,
                        "Error: " + "Connection fail!",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_signout:
                Intent intent = new Intent(getApplicationContext(),RegisterScreen.class);
                startActivity(intent);
        }
    }
}

