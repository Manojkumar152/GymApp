package app.com.gymapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.thomashaertel.widget.MultiSpinner;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.com.gymapp.ApiCalls.ApiInterface;
import app.com.gymapp.ApiCalls.RetrofitClient;
import app.com.gymapp.Models.RegistrationDetails;
import app.com.gymapp.Util.mDateFormat;
import es.dmoral.toasty.Toasty;
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
    private ExpandableLayout expandableLayout2, expandableLayout3;
    private Validator validator;
    private String  TAG = "Register",bDate = "";
    private MultiSpinner spnClass, spnGroup;
    private List<RegistrationDetails.ResultBean.ClassBean> classList = new ArrayList<>();
    private List<RegistrationDetails.ResultBean.GroupBean> groupList = new ArrayList<>();
    private ArrayAdapter<String> classAdapter, groupAdapter;

    private List<String> class_List = new ArrayList<>();
    private List<String> group_List = new ArrayList<>();
    String name ="";
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

        expandableLayout2 = findViewById(R.id.expandable2);
        expandableLayout2.setOnExpansionUpdateListener(this);
        expandableLayout3 = findViewById(R.id.expandable3);
        expandableLayout3.setOnExpansionUpdateListener(this);
        dob = findViewById(R.id.DOB);
        dob.setOnClickListener(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        div_contact = findViewById(R.id.div_contact);
        div_login = findViewById(R.id.div_login);
        div_contact.setOnClickListener(this);
        div_login.setOnClickListener(this);

        spnClass = findViewById(R.id.spnClass);
        spnGroup = findViewById(R.id.spnGroup);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                Intent intent= new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.div_contact:
                expandableLayout2.toggle();
                break;

            case  R.id.div_login:
                expandableLayout3.toggle();
                expandableLayout2.collapse();
                break;

            case R.id.DOB:
                getBirthDate();
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
                    classList =response.body().getResult().getClassX();
                    groupList = response.body().getResult().getGroup();
                    getAdapterDetail();
                   // class_List.addAll(classList);

                }else{
                    Toasty.error(getApplicationContext(),response.body().getError());
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<RegistrationDetails> call, Throwable t) {
                /*Toasty.warning(RegisterScreen.this,
                        "Error: " + "Connection fail!",Toast.LENGTH_LONG).show();
             */   dialog.dismiss();
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

    private void getBirthDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        dob.setText(new mDateFormat().mFormat(year + "-" + monthOfYear + "-" + dayOfMonth));
                        bDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                        validator.removeRules(dob);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void validation() {


        if (TextUtils.isEmpty(fName.getText().toString().trim())) {
            fName.setError("Please Enter Your First Name");
            return;
        }
        if (TextUtils.isEmpty(mName.getText().toString().trim())) {
            mName.setError("Please Enter Your Middle Name");
            return;
        }
        if (TextUtils.isEmpty(lName.getText().toString().trim())) {
            lName.setError("Please Enter Your Last Name");
            return;
        }
        if (TextUtils.isEmpty(rAddr.getText().toString().trim())) {
            rAddr.setError("Please Enter Your Address");
            return;
        }
        if (TextUtils.isEmpty(rCity.getText().toString().trim())) {
            rCity.setError("Please Enter Your City");
            return;
        }
        if (TextUtils.isEmpty(rState.getText().toString().trim())) {
            rState.setError("Please Enter Your state");
            return;
        }
        if (TextUtils.isEmpty(rZip.getText().toString().trim())) {
            rZip.setError("Please Enter Your Zipcode");
            return;
        }
        if (TextUtils.isEmpty(rMobile.getText().toString().trim())) {
            rMobile.setError("Please Enter Your Mobile Number");
            return;
        }
        if (TextUtils.isEmpty(rEmail.getText().toString().trim()) && Patterns.EMAIL_ADDRESS.matcher(rEmail.getText().toString().trim()).matches()) {
            rEmail.setError("Please Enter Your Email");
            return;
        }

       // uploadImage();
    }

    private void getAdapterDetail(){
        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < classList.size(); i++) {
            classAdapter.add(classList.get(i).getClass().getName());
        }
        spnClass.setAdapter(classAdapter, false, onSelectedClassListener);
        //Group Adapter
        groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < groupList.size(); i++) {
            groupAdapter.add(groupList.get(i).getName());
        }
        spnGroup.setAdapter(groupAdapter, false, onSelectedGroupListener);

    }

    private MultiSpinner.MultiSpinnerListener onSelectedClassListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            validator.removeRules(spnClass);
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    class_List.add(classAdapter.getItem(i));
                  //  class_List.add(classAdapter.getItem(1));
                }
            }
        }
    };
    private MultiSpinner.MultiSpinnerListener onSelectedGroupListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            validator.removeRules(spnGroup);
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    group_List.add(groupAdapter.getItem(i));
                }
            }
        }
    };
}
