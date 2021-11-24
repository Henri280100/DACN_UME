package com.example.ume_frontend.ui.activity;

import static com.example.ume_frontend.Retrofit.RetrofitClient.getRetrofit;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ume_frontend.API.ApiMethod;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;
import com.example.ume_frontend.ui.ForgotPassword.ForgotPasswordActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtForgotPassword;
    private Button btnSignin, btnLogin;
    private TextInputLayout txtInputEmailPhone, txtInputPassword;
    private Handler handler;
    public static UserModel.Data myAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtForgotPassword = findViewById(R.id.txtForgotPass);
        txtInputEmailPhone = findViewById(R.id.txtUserAccount);
        txtInputPassword = findViewById(R.id.txtPassword);
        myAccount = null;

        handler = new Handler();

        ///////// LOGIN ////////
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtInputEmailPhone.getEditText().getText())) {
                    txtInputEmailPhone.setError("Field cannot be empty!");
                } else if (TextUtils.isEmpty(txtInputPassword.getEditText().getText())) {
                    txtInputPassword.setError("What is your password?");
                } else {
                    getUser();
                }
            }
        });
        /// SIGN IN////
        btnSignin = findViewById(R.id.btnRegister);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        //// PUSH-DOWN ButtON ANIMATION
        PushDownAnim.setPushDownAnimTo(btnLogin, btnSignin)
                .setScale(MODE_SCALE, 0.89f)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);

        //// FORGOT PASSWORD ///////
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    // Get Api for user login
    private void getUser() {
        ApiMethod method = getRetrofit().create(ApiMethod.class);
        Call<UserModel> call = method.getUser(txtInputEmailPhone.getEditText().getText().toString(),
                txtInputPassword.getEditText().getText().toString());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body().getMessage().equals("success")) {
                    Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    UserModel.Data data = response.body().getData();
                    myAccount = data;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, UserLandingPage.class);
                            startActivity(intent);
                        }
                    }, 700);
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.v("log:", t.getMessage());
            }
        });
    }
}