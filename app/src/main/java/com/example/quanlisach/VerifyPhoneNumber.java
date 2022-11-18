package com.example.quanlisach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyPhoneNumber extends AppCompatActivity {

    EditText OTP;
    Button XacNhan;
    TextView sendOTPAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        Verify();
        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendOTP = OTP.getText().toString().trim();
                OnClickSendOTP();
            }
        });

        sendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnClickSendOTPAgain();
            }
        });
    }





    private void Verify () {
        OTP = findViewById(R.id.textOTP);
        XacNhan = findViewById(R.id.BtnOtp);
        sendOTPAgain = findViewById(R.id.SendOTPAgain);
    }
    private void OnClickSendOTP() {

    }
    private void OnClickSendOTPAgain() {

    }
}