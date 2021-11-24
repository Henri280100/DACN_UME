package com.example.ume_frontend.ui.activity;

import static com.example.ume_frontend.Retrofit.RetrofitClient.getRetrofit;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ume_frontend.API.ApiMethod;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;
import com.google.android.material.textfield.TextInputLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView imgBack;
    TextInputLayout txtEmail, txtPhoneNumber;
    TextInputLayout txtPassword, txtConfirmPassword;
    TextView txtAlreadyHaveAccount, txtLogin;
    TextInputLayout txtUsername;
    RadioButton rdMale, rdFemale;
    RadioGroup rgGender;
    ConstraintLayout layoutVerify;
    RelativeLayout layoutSignin;
    Button btnOk, btnSend;
    Button btnDone;
    EditText txtVerifyCode;
    String verifyCode;
    String gender = "1";
    Handler handler;
    ProgressDialog progressDialog;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imgBack = findViewById(R.id.imgBack);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumb);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnDone = findViewById(R.id.btnDone);

        txtAlreadyHaveAccount = findViewById(R.id.txtAlreadyHaveAccount);
        txtLogin = findViewById(R.id.txtLogin);
        txtUsername = findViewById(R.id.txtUsername);
        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);
        rgGender = findViewById(R.id.rgGender);
        layoutSignin = findViewById(R.id.layoutSignin);
        layoutVerify = findViewById(R.id.layoutVerification);


        btnOk = findViewById(R.id.btnOk);
        btnSend = findViewById(R.id.btnSend);
        txtVerifyCode = findViewById(R.id.txtVerifyCode);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Just a minute");
        progressDialog.setCanceledOnTouchOutside(false);
//        final String[] numberToPass = {"1"}; // 1 for male
//        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                int childCount = radioGroup.getChildCount();
//                RadioButton btn = (RadioButton) radioGroup.findViewById(childCount);
//                switch (btn.getId()){
//                    case R.id.rdMale:
//                        numberToPass[0] = "1";
//                        break;
//                    case R.id.rdFemale:
//                        numberToPass[0] = "2";
//                        break;
//                }
//            }
//        });

        // PUSH-DOWN BUTTON ANIMATION
        PushDownAnim.setPushDownAnimTo(btnDone, btnOk, btnSend)
                .setScale(MODE_SCALE, 0.90f)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        /**
         * TEXTVIEW LOGIN
         * (NOTE: IF USER ALREADY HAS ACCOUNT)
         */
        txtLogin.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        /**
         * BACK FUNCTION
         * Hmm...Just create this back function if user don't wanna use the login textview
         * Just for fun :D
         */
        imgBack.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        rgGender.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.rdMale:
                        gender = "1";
                        break;
                    case R.id.rdFemale:
                        gender = "2";
                        break;
                }
            }
        });

        /// BUTTON DONE ////
        btnDone.setOnClickListener(view -> {
            verificationEmail(Objects.requireNonNull(txtEmail.getEditText()).getText().toString());
            layoutSignin.setVisibility(View.GONE);
            layoutVerify.setVisibility(View.VISIBLE);
            progressDialog.show();
        });
        btnOk.setOnClickListener(view -> {
            try {

                if (txtVerifyCode.getText().toString().equals(verifyCode)) {
                    createUser();
                    layoutVerify.setVisibility(View.GONE);
                    layoutSignin.setVisibility(View.VISIBLE);
                } else {
                    txtVerifyCode.setError("Wrong verification code!");
                    txtVerifyCode.setText("");
                    txtVerifyCode.requestFocus();
                }
            } catch (NullPointerException ex) {
                txtVerifyCode.setError("Something wrong");
                ex.getMessage();
            }
        });
        // Send email again if users haven't received any mail.
        btnSend.setOnClickListener(view -> {
            verificationEmail(txtEmail.getEditText().getText().toString());
            progressDialog.show();
        });

    }


    private void verificationEmail(String email) {
        ApiMethod method = getRetrofit().create(ApiMethod.class);
        Call<String> call = method.sendCodeToEmail(email);
        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    verifyCode = response.body();
                    assert verifyCode != null;
                    if (!verifyCode.equals("") && response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Please check your mail", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        txtVerifyCode.setError("Wrong email!");
                        txtVerifyCode.setText("");
                        txtVerifyCode.requestFocus();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, t.getLocalizedMessage());
                    Log.e(TAG, t.getMessage());
                    t.printStackTrace();
//                    txtVerifyCode.setError("Please check your wifi");

                }
            });
        } catch (Exception ex) {
            txtVerifyCode.setError("Something wrong");
        }
    }

    private void createUser() {
        UserModel.Data userAccount = new UserModel.Data(null, null, rgGender.toString(),
                null, null, null, txtUsername.getEditText().getText().toString(),
                null, txtPassword.getEditText().getText().toString(), txtPhoneNumber.getEditText().getText().toString(),
                null, null, txtEmail.getEditText().getText().toString());
        ApiMethod method = getRetrofit().create(ApiMethod.class);
        Call<UserModel> call = method.createUser(userAccount);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body().getMessage().equals("success")) {
                    Log.d("Log", "Add successful");
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("Log", t.getMessage());
            }
        });
    }


}