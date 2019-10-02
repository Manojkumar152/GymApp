package app.com.gymapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout mcreate_account;
    private TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        initView();
    }

    private void initView() {
        mcreate_account=findViewById(R.id.create_account);
        signIn=findViewById(R.id.sign_in);

        mcreate_account.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.create_account:
               // Intent intent= new Intent(WelcomeScreen.this,RegisterScreen.class);
                Intent intent= new Intent(WelcomeScreen.this,RegisterScreen.class);
                startActivity(intent);
                break;
            case R.id.sign_in:
                Intent intent1= new Intent(WelcomeScreen.this,LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
