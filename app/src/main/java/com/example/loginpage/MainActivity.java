package com.example.loginpage;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginpage.net.ApiClient;
import com.example.loginpage.net.LoginResponse;
import com.example.loginpage.net.WebServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.login_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uEt = findViewById(R.id.user_name_edit_text);
                EditText pEt = findViewById(R.id.password_edit_text);

                String user = uEt.getText().toString();
                String pass = pEt.getText().toString();

                callLoginService(user, pass);
            }
        });
    }

    private void callLoginService(String user, String pass) {
        Retrofit r = ApiClient.getClient();
        WebServices s = r.create(WebServices.class);

        Call<LoginResponse> call = s.login(user, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse body = response.body();
                if (body != null) {
                    int errorCode = body.errorCode;
                    if (errorCode == 0) {
                        boolean loginStatus = body.loginStatus;

                        if (loginStatus) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("เข้าสู่ระบบสำเร็จ")
                                    .show();
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("ชื่อผู้ใช้งาน หรือรหัสผ่าน ไม่ถูกต้อง")
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }
}
