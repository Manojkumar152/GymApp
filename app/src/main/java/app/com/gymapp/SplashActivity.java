package app.com.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    private static final String TAG = "Splash Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager session = new SessionManager(getApplicationContext());
                Log.e(TAG,"SessionLogin"+ session.isLoggedIn());
                if (session.isLoggedIn()) {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    session.checkLogin();
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
