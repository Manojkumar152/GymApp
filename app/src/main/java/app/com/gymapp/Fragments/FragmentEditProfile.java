package app.com.gymapp.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import app.com.gymapp.ApiCalls.ApiInterface;
import app.com.gymapp.ApiCalls.RetrofitClient;
import app.com.gymapp.BuildConfig;
import app.com.gymapp.HomeActivity;
import app.com.gymapp.Models.EditProfile;
import app.com.gymapp.Models.ProfileData;
import app.com.gymapp.R;
import app.com.gymapp.SessionManager;
import app.com.gymapp.Util.mDateFormat;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by monika on 01-10-2019.
 */

public class FragmentEditProfile extends Fragment implements View.OnClickListener, Validator.ValidationListener {
    @Nullable
    private String TAG = FragmentEditProfile.class.getSimpleName();
    private String bDate = "";
    @Nullable
    private EditText tv_edit_username;
    boolean flag = true;
    private EditText tv_edit_lname;
    @Length(min = 6, message = "Should be greater than 6 character")
    @Email
    private EditText tv_edit_email;
    @Length(min = 12, message = "Miminum length is 12")
    private EditText tv_edit_mobileNo;
    private EditText tv_edit_name;
    @Length(min = 6, message = "Should be greater than 6 character")
    private EditText tv_edit_address;
    private EditText tv_edit_birth;
    private EditText tv_edit_newPass, tv_edit_confirmPass;
    String picturePath = "";
    private Button save;
    private String img = "";
    private int i = 0;
    private boolean isEdited;
    private Bitmap bitmap;
    TextView txtname,txtaddress;
    private ImageView  click_name, click_address, click_email, click_mobile, click_birth, click_currentPass, click_newPass, click_confirmPass;
    private HomeActivity aContext;
    private ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Validator validator;
    private CircleImageView imageView;
    private ArrayList<EditProfile> editProfile;
    private String pass = "";
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.wait));

        initView(view);
        getUserProfile();
        logout(view);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEdited==true) {
                    final String emailText = tv_edit_email.getText().toString();
                    EMAIL_ADDRESS_PATTERN.matcher(emailText).matches();
                    validator.validate();
                }
                else{
                    Snackbar.make(getActivity().getWindow().getDecorView(),"Nothing to update",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    private void initView(View view) {
        isEdited=false;
        validator = new Validator(this);
        validator.setValidationListener(this);
        txtname = view.findViewById(R.id.v_fname);
        txtaddress = view.findViewById(R.id.v_address);
        imageView = view.findViewById(R.id.image_view);

        tv_edit_username = view.findViewById(R.id.username_view);
        tv_edit_name = view.findViewById(R.id.name_view);
        tv_edit_lname = view.findViewById(R.id.lname_view);
        tv_edit_address = view.findViewById(R.id.address_view);
        tv_edit_email = view.findViewById(R.id.email_view);
        tv_edit_mobileNo = view.findViewById(R.id.mobile_no_view);
        tv_edit_birth = view.findViewById(R.id.date_of_birth_view);
        tv_edit_newPass = view.findViewById(R.id.new_password_view);
        tv_edit_confirmPass = view.findViewById(R.id.confirm_password_view);
        imageView.setOnClickListener(this);
        save = view.findViewById(R.id.edit_save);

        click_name = view.findViewById(R.id.click_name);
        click_address = view.findViewById(R.id.click_address);
        click_email = view.findViewById(R.id.click_email);
        click_mobile = view.findViewById(R.id.click_mobile);
        click_birth = view.findViewById(R.id.click_birth_date);

        click_name.setOnClickListener(this);
        click_address.setOnClickListener(this);
        click_email.setOnClickListener(this);
        click_mobile.setOnClickListener(this);
        click_birth.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aContext.setActionBarTitle(getResources().getString(R.string.viewprofile));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HomeActivity) {
            aContext = (HomeActivity) activity;
        } else {
            throw new ClassCastException();
        }
    }

    private void getUserProfile() {
        progressDialog.show();
        final String id = new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ID);
        ApiInterface service = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ProfileData> call = service.getUserProfile(id);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                Log.e(TAG,"OnResponse"+response.body());
                progressDialog.dismiss();
                if(response.body().getStatus().equals("1")) {
                    try {
                        Picasso.with(getContext())
                                .load(response.body().getResult().get(0).getImage())
                                .fit()
                                //.placeholder(getResources().getDrawable(R.drawable.ic_placeholder))
                                .centerCrop()
                                .into(imageView);
                        txtname.setText(response.body().getResult().get(0).getFirst_name()+response.body().getResult().get(0).getLast_name());
                        txtaddress.setText(response.body().getResult().get(0).getAddress());
                        tv_edit_username.setText(response.body().getResult().get(0).getUsername());
                        tv_edit_name.setText(response.body().getResult().get(0).getFirst_name());
                        tv_edit_lname.setText(response.body().getResult().get(0).getLast_name());
                        tv_edit_address.setText(response.body().getResult().get(0).getAddress());
                        tv_edit_email.setText(response.body().getResult().get(0).getEmail());
                        tv_edit_mobileNo.setText(response.body().getResult().get(0).getMobile());
                        bDate = response.body().getResult().get(0).getBirth_date();
                        tv_edit_birth.setText(new mDateFormat().mFormat(bDate));
                        pass = response.body().getResult().get(0).getPassword();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toasty.error(getActivity(),response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {

            }
        });
    }

                    /*params.put("username", username);
                    params.put("first_name", fname);
                    params.put("last_name", lname);
                    params.put("address", address);
                    params.put("email", email);
                    params.put("birth_date", bDate);
                    // params.put("image",fileName);
                    params.put("mobile", mobile);
                    params.put("password", finalNewPass);
                    Log.d("finalNewPass", finalNewPass);
                    // params.put("image",);
                    params.put("id", id);
                    //  params.put(KEY_PASSWORD,password);
                    //params.put(KEY_EMAIL, email);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }*/
    //}

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.click_username:
                break;
            case R.id.click_name:
                isEdited=true;
                tv_edit_name.setEnabled(true);
                tv_edit_lname.setEnabled(true);
                break;
            case R.id.click_address:
                isEdited=true;
                tv_edit_address.setEnabled(true);
                break;
            case R.id.click_email:
                isEdited=true;
                tv_edit_email.setEnabled(true);
                break;
            case R.id.click_mobile:
                isEdited=true;
                tv_edit_mobileNo.setEnabled(true);
                break;
            case R.id.click_birth_date:
                isEdited=true;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear++;
                                tv_edit_birth.setText(new mDateFormat().mFormat(year + "-" + monthOfYear + "-" + dayOfMonth));
                                bDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                                validator.removeRules(tv_edit_birth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
           /* case R.id.click_currentPass:
                break;*/

            case R.id.image_view:
                isEdited=true;
                pickImage();
                break;
        }
    }

    private void logout(View view) {
        ImageButton logout = view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SessionManager(getActivity()).logoutUser();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        updateUserProfile();
    }

    private void updateUserProfile() {
        progressDialog.show();
        final String username = tv_edit_username.getText().toString().trim();
        final String fname = tv_edit_name.getText().toString().trim();
        final String lname = tv_edit_lname.getText().toString().trim();
        final String address = tv_edit_address.getText().toString().trim();
        final String email = tv_edit_email.getText().toString().trim();
        final String mobile = tv_edit_mobileNo.getText().toString().trim();
        String newPass = tv_edit_newPass.getText().toString().trim();
        final String confirmPass = tv_edit_confirmPass.getText().toString().trim();
        if (!newPass.equals(confirmPass)) {
            Toasty.info(getActivity(), "New password and confirm password must be same!", Toast.LENGTH_SHORT).show();
        } else {
            if (newPass.isEmpty()) {
                newPass = "";
            }
            final String id = new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ID);
            final String finalNewPass = newPass;

            ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call= service.updateUserProfile(id,username,fname,lname,address,email,mobile,finalNewPass);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e(TAG,"UPDAte"+response.body().toString());
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pickImage() {
        if (checkPermission()) {
            Log.w(TAG, "Permission Granted");
            getImageFromStorage();
        } else {
            storagePermission();
        }
    }

    private void storagePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkPermission())
                requestPermission();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toasty.info(getActivity(), "Read External Storage permission allows us to get images.", Toast.LENGTH_LONG).show();
            storagePermission();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 23:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted");
                    getImageFromStorage();
                } else {
                    requestPermission();
                }
                break;
        }
    }

    private void getImageFromStorage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            performCrop(picturePath);
        }

        if (requestCode == 22) {
            if (resultCode == Activity.RESULT_OK) {
                flag = false;
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                imageView.setImageBitmap(selectedBitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                bitmap = selectedBitmap;
                uploadImage();
                //
            }
        }
    }

    private void performCrop(String picUri) {
        try
        {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            File f = new File(picUri);
            Uri contentUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                contentUri = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider", f);
            } else {
                contentUri = Uri.fromFile(f);
            }
            cropIntent.setDataAndType(contentUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 22);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        /*final StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.w("res", s);
                        loading.dismiss();
                        try {
                            JSONObject obj = new JSONObject(s);
                            String status = obj.getString("status");
                            if (status.equals("1")) {
                                aContext.changeImage();
                                Picasso.with(getContext())
                                        .load(img)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(getResources().getDrawable(R.drawable.ic_placeholder))
                                        .fit()
                                        .into(imageView);
                                Toasty.success(aContext, "Profile image successfully updated", 0).show();
                            } else {
                                Toasty.error(getContext(), obj.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.w("Error", e.getMessage());
                            Toasty.error(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toasty.error(getActivity(), "Network Error!", Toast.LENGTH_LONG).show();
                        Log.w("Error", "" + volleyError.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String image = getStringImage(bitmap);
                Map<String, String> params = new Hashtable<>();
                params.put("image", image);
                params.put("name", editProfile.get(i).getFileName());
                //  params.put("Old ",editProfile.get(i).getFileName() );
                // params.put("id",new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ID));
                Log.d("fileName", editProfile.get(i).getFileName());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);*/

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("EncodeImage", encodedImage + "");
        return encodedImage;
    }
}





