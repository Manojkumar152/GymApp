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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import app.com.gymapp.BuildConfig;
import app.com.gymapp.HomeActivity;
import app.com.gymapp.Models.EditProfile;
import app.com.gymapp.R;
import app.com.gymapp.SessionManager;
import app.com.gymapp.Util.mDateFormat;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

/**
 * Created by monika on 01-10-2019.
 */

public class Fragment_Edit_Profile extends Fragment implements View.OnClickListener, Validator.ValidationListener {
    @Nullable
    private String TAG = Fragment_Edit_Profile.class.getSimpleName();
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
    private Bitmap bitmap;
    private ImageView click_username, click_name, click_address, click_email, click_mobile, click_birth, click_currentPass, click_newPass, click_confirmPass;
    private HomeActivity aContext;
    private ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Validator validator;
    private CircleImageView imageView;
    private ArrayList<EditProfile> editProfile;
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

        logout(view);

        validator = new Validator(this);
        validator.setValidationListener(this);
        TextView txtname = view.findViewById(R.id.v_fname);
        TextView txtaddress = view.findViewById(R.id.v_address);
        imageView = view.findViewById(R.id.image_view);
        txtname.setText(new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_NAME));
        txtaddress.setText(new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ADDR));
        // imageView.setImageResource(new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_IMAGE));
        /*Picasso.with(getContext())
                .load(new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_IMAGE))
                .fit()
                .into(imageView);*/
        // Log.d("image", new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_IMAGE));

        tv_edit_username = view.findViewById(R.id.username_view);
        tv_edit_name = view.findViewById(R.id.name_view);
        tv_edit_lname = view.findViewById(R.id.lname_view);
        tv_edit_address = view.findViewById(R.id.address_view);
        tv_edit_email = view.findViewById(R.id.email_view);
        tv_edit_mobileNo = view.findViewById(R.id.mobile_no_view);
        tv_edit_birth = view.findViewById(R.id.date_of_birth_view);
        // tv_edit_currentPass = (EditText) view.findViewById(R.id.current_password_view);
        tv_edit_newPass = view.findViewById(R.id.new_password_view);
        tv_edit_confirmPass = view.findViewById(R.id.confirm_password_view);

        save = view.findViewById(R.id.edit_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailText = tv_edit_email.getText().toString();
                EMAIL_ADDRESS_PATTERN.matcher(emailText).matches();
                validator.validate();


            }
        });
        imageView.setOnClickListener(this);

        /*============================================================================================*/
        /*edit_username=(EditText)view.findViewById(R.id.username_edit);
        edit_name=(EditText)view.findViewById(R.id.name_edit);
        edit_address=(EditText)view.findViewById(R.id.address_edit);
        edit_email=(EditText)view.findViewById(R.id.email_edit);
        edit_mobileNo=(EditText)view.findViewById(R.id.mobile_no_edit);
        edit_birth=(EditText)view.findViewById(R.id.birth_edit);
        edit_currentPass=(EditText)view.findViewById(R.id.current_password_edit);
        edit_newPass=(EditText)view.findViewById(R.id.new_password_edit);
        edit_confirmPass=(EditText)view.findViewById(R.id.confirm_password_edit);*/

        click_username = view.findViewById(R.id.click_username);
        click_name = view.findViewById(R.id.click_name);
        click_address = view.findViewById(R.id.click_address);
        click_email = view.findViewById(R.id.click_email);
        click_mobile = view.findViewById(R.id.click_mobile);
        click_birth = view.findViewById(R.id.click_birth_date);
        //click_currentPass = (ImageView) view.findViewById(R.id.click_currentPass);
        //   click_newPass = (ImageView) view.findViewById(R.id.click_newPass);
        // click_confirmPass = (ImageView) view.findViewById(R.id.click_confirmPass);


        click_name.setOnClickListener(this);
        // click_username.setOnClickListener(this);
        click_address.setOnClickListener(this);
        click_email.setOnClickListener(this);
        click_mobile.setOnClickListener(this);
        click_birth.setOnClickListener(this);
        // click_currentPass.setOnClickListener(this);
        // click_newPass.setOnClickListener(this);
        // click_confirmPass.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();

        return view;
    }

      /*  parser = new JsonParser(aContext);

        final StringRequest req = new StringRequest(Request.Method.POST, Const.URL_EDITPROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                progressDialog.dismiss();
                Log.d(TAG, response);

                try {
                    editProfile = parser.getEditProfile(response);
                    img = editProfile.get(i).getImage();
                    Picasso.with(getContext())
                            .load(img)
                            .fit()
                            //.placeholder(getResources().getDrawable(R.drawable.ic_placeholder))
                            .centerCrop()
                            .into(imageView);
                    tv_edit_username.setText(editProfile.get(i).getUsername());
                    tv_edit_name.setText(editProfile.get(i).getFirstName());
                    tv_edit_lname.setText(editProfile.get(i).getLastName());
                    tv_edit_address.setText(editProfile.get(i).getAddress());
                    tv_edit_email.setText(editProfile.get(i).getEmail());
                    tv_edit_mobileNo.setText(editProfile.get(i).getMobile());
                    bDate = editProfile.get(i).getBirthDate();
                    tv_edit_birth.setText(new mDateFormat().mFormat(bDate));
                    Log.d("Password", editProfile.get(i).getPassword());
                    pass = editProfile.get(i).getPassword();

                    Log.d("Image View", editProfile.get(i).getImage());

                    // tv_edit_currentPass.setText(editProfile.get(i).getPassword());
                    // tv_edit_newPass.setText(editProfile.get(i).getUsername());
                    // tv_edit_confirmPass.setText(editProfile.get(i).getUsername());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ID));
                Log.d("Id", new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ID));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(req);
        return view;
    }*/



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

    private void editprofile() {
        final String username = tv_edit_username.getText().toString().trim();
        final String fname = tv_edit_name.getText().toString().trim();
        final String lname = tv_edit_lname.getText().toString().trim();
        final String address = tv_edit_address.getText().toString().trim();
        final String email = tv_edit_email.getText().toString().trim();
        final String mobile = tv_edit_mobileNo.getText().toString().trim();


        String newPass = tv_edit_newPass.getText().toString().trim();
        //   final String image=imageView.setImageDrawable(imageView.getDrawable().);
        final String confirmPass = tv_edit_confirmPass.getText().toString().trim();
        if (!newPass.equals(confirmPass)) {
            Toasty.info(getActivity(), "New password and confirm password must be same!", Toast.LENGTH_SHORT).show();
        } else {
            if (newPass.isEmpty()) {
                newPass = "";
            }
           /* // final String password = editTextPassword.getText().toString().trim();
            // final String email = editTextEmail.getText().toString().trim();
            //
            final String id = new SessionManager(getActivity()).getUserDetails().get(SessionManager.KEY_ID);
            final String finalNewPass = newPass;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL_INSERT_PROFILE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  progressDialog.dismiss();
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                                String status = obj.getString("status");
                                String error = obj.getString("error");
                                if (status.equals("1")) {
                                    aContext.updateName(fname + " " + lname);
                                    Toasty.success(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                } else
                                    Toasty.error(getContext(), error, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //progressDialog.dismiss();
                            Toasty.error(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
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
            AppController.getInstance().addToRequestQueue(stringRequest);*/
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.click_username:
                //tv_edit_username.setEnabled(true);
                //tv_edit_username.setCursorVisible(true);
                break;
            case R.id.click_name:
                tv_edit_name.setEnabled(true);
                tv_edit_lname.setEnabled(true);
                break;
            case R.id.click_address:
                tv_edit_address.setEnabled(true);
                break;
            case R.id.click_email:
                tv_edit_email.setEnabled(true);
                break;
            case R.id.click_mobile:
                tv_edit_mobileNo.setEnabled(true);
                break;
            case R.id.click_birth_date:
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
        editprofile();
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





