package com.example.dell.filmcinema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtEmail,edtPass;
    Button btnLogin,btnres,btnforget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        addControls();
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        btnforget=findViewById(R.id.btnforget);
        btnres=findViewById(R.id.btnre);
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        btnLogin=findViewById(R.id.btnLogin);
    }
    private void resetPassword(){
        String email=edtEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(EmailAuthActivity.this, "Nhập email của bạn ", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(edtEmail.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EmailAuthActivity.this, "Đặt lại mật khẩu trong email", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EmailAuthActivity.this, "Bạn phải nhập email", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    public void createAccount(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(EmailAuthActivity.this, "Bạn đã đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("mes", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }
    private void signIn(String email,String pass){
        final ProgressDialog progressDialog=ProgressDialog.show(EmailAuthActivity.this,"Please wait...","Progressing....",true);
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(EmailAuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            Toast.makeText(EmailAuthActivity.this, "signIn is succsess", Toast.LENGTH_SHORT).show();
                            if(user.getEmail().equals("daohauth@gmail.com")){
                                startActivity(new Intent(EmailAuthActivity.this,AddFilm.class));

                            }else{
                                Intent intent= new Intent(EmailAuthActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                        }else{
                            Log.i("loi",task.getException()+"");
                            Toast.makeText(EmailAuthActivity.this, "Authen is failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void sendEmailVerification(){
        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(EmailAuthActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(EmailAuthActivity.this, "Tài khoản đã được xác nhận", Toast.LENGTH_SHORT).show();
                       }

                    }
                });
    }


    public void OnClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                signIn(edtEmail.getText().toString().trim(),edtPass.getText().toString().trim());
                break;
            case R.id.btnre:
                startActivity(new Intent(EmailAuthActivity.this,DangKi.class));
                break;
            case R.id.btnforget:
                resetPassword();
                break;
        }

    }
}
