package com.example.quanlisach;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlisach.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SignUpActivite extends AppCompatActivity {

    private static final String TAG = SignUpActivite.class.getName();

    EditText Gmail,MatKhau,NhapLaiMatKhau;

    private  String  mail,pass,passAgain;

    private Button DangKy;

    ValidateInput validateInput;

    private FirebaseAuth auth;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DatabaseReference mDatabase;
    long id = new Random().nextInt(900001) + 10000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up_activite);

        Gmail = findViewById(R.id.textGmail);
        MatKhau = findViewById(R.id.textPass);
        NhapLaiMatKhau = findViewById(R.id.textPassAganim);



        DangKy = findViewById(R.id.BtnDangKy);

        validateInput = new ValidateInput(this);

        DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangkyAccount();
            }
        });

        auth = FirebaseAuth.getInstance();

    }

    private void DangkyAccount() {
        mail = Gmail.getText().toString();
        pass = MatKhau.getText().toString();

        passAgain = NhapLaiMatKhau.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();



        if (validateInput.checkEmail(mail) && validateInput.checkPass(pass))
        {
            if (pass.equals(passAgain))
            {
                auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivite.this, "Đăng kí thành Công", Toast.LENGTH_SHORT).show();
                        String Avartar = "https://firebasestorage.googleapis.com/v0/b/doanjava-8a230.appspot.com/o/avartar.jpg?alt=media&token=72941b9c-aa10-4b7c-970a-ea6212164999";
                        User user = new User(Avartar,mail,id);
                            AddUser(user);


                    }
                });{

            }

            }
            else {
                Toast.makeText(this, "Mật khẩu không trùng hãy nhập lại", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void AddUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Account");

        String pathObject = String.valueOf("User"+user.getId());
        myRef.child(pathObject).setValue(user);
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }





}