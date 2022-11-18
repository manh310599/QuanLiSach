package com.example.quanlisach;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlisach.Tam.Tam;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Gmail,MatKhau;
    private Button DangNhap,DangKy;
    TextView ForgotPass;

    ValidateInput validateInput;



    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        validateInput = new ValidateInput(this);

        Gmail = findViewById(R.id.textGmail);
        MatKhau = findViewById(R.id.textPass);
        DangNhap = findViewById(R.id.btnDangNhap);
        DangKy = findViewById(R.id.BtnDangKy);
        ForgotPass = findViewById(R.id.ForgotPass);

        DangNhap.setOnClickListener(this);
        DangKy.setOnClickListener(this);
        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnclickForgotPass();
            }
        });

        auth = FirebaseAuth.getInstance();

    }

    private void OnclickForgotPass() {
        Intent intent = new Intent(MainActivity.this,ForgotPass.class);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnDangNhap:
                NutdangNhap();
                break;

            case R.id.BtnDangKy:
                NutDangKy();
                break;

        }
    }

    private void NutDangKy() {
        Intent intent = new Intent(this,SignUpActivite.class);
        startActivity(intent);

    }

    private void NutdangNhap() {
        String email = Gmail.getText().toString();
        String pass = MatKhau.getText().toString();

        if (validateInput.checkEmail(email) && validateInput.checkPass(pass))
        {
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,BottomBar.class);
                    startActivity(intent);
                    Tam.UserName = Gmail.getText().toString().trim();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



}