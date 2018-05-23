package com.example.dell.filmcinema;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static boolean validateForm(String email, String password,Context context) {
        if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            Toast.makeText(context, "Nhập đúng định dạng của email, ví dụ huyen@savvycom.com", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context.getApplicationContext(), "Nhập địa chỉ email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context.getApplicationContext(), "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6 || password.length()>32) {
            Toast.makeText(context.getApplicationContext(), "Mật khẩu phải từ 6-32 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    public static String saveToInternalStorage(Bitmap bitmapImage, String profile, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, profile + ".jpeg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

}
