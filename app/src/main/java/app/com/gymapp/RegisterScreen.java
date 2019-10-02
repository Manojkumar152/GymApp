package app.com.gymapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import app.com.gymapp.ApiCalls.ApiInterface;
import app.com.gymapp.ApiCalls.RetrofitClient;
import app.com.gymapp.Models.RegistrationDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity implements  View.OnClickListener,ExpandableLayout.OnExpansionUpdateListener, Validator.ValidationListener{
    private Button submitBtn;
    private ProgressDialog dialog;
    private TextView dob, rJoinDate;
    private TextView div_personal, div_contact, div_more, div_login, mId;
    //    @NotEmpty
    private EditText mName;
    //    @NotEmpty
    private EditText fName, lName, rAddr, rCity, rState, rUname, rPassword;
    //    @NotEmpty
    private EditText rMobile;
    //    @NotEmpty
    private EditText rZip;
    //    @NotEmpty
    private EditText rEmail;
    private ExpandableLayout el1, el2, el3, el4;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        initViews();
        getRegistrationDetails();
    }

    private void initViews() {
        submitBtn= findViewById(R.id.submit);
        mId = findViewById(R.id.rMId);
        imageView = findViewById(R.id.MImage);
        imageView.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.wait));
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                Intent intent= new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getRegistrationDetails() {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<RegistrationDetails> call= service.getRegistrationDetails();
        call.enqueue(new Callback<RegistrationDetails>() {
            @Override
            public void onResponse(Call<RegistrationDetails> call, Response<RegistrationDetails> response) {
                dialog.dismiss();
                if(response.body().getStatus().equals("1")){
                    mId.setText(response.body().getResult().getMember_id());
                }
            }

            @Override
            public void onFailure(Call<RegistrationDetails> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }

    @Override
    public void onExpansionUpdate(float expansionFraction) {

    }
}
