package com.example.quanlisach;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class ValidateInput {

    Context context;

    ValidateInput(Context context)
    {
        this.context = context;
    }

    boolean checkEmail(String email)
    {
        if (email.length()==0)
        {
            Toast.makeText(context, "Hãy nhập vào mật email của bạn", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) //kiểm tra định dạng email
        {

            Toast.makeText(context, "Sai định dạng mail xin hãy đăng nhập lại", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {

            return true;
        }

    }

    boolean checkPass(String Pass)
    {
        if (Pass.length()==0)
        {
            Toast.makeText(context, "Hãy nhập mật khẩu của bạn", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Pass.length()<6)
        {
            Toast.makeText(context, "Mật khẩu phải có từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {

            return true;
        }

    }
}
