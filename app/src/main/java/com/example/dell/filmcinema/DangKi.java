package com.example.dell.filmcinema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKi extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edtEamil,edtPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
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
        edtEamil=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);

    }

    public void onClick(View view) {
        if (!Utils.validateForm(edtEamil.getText().toString().trim(),edtPass.getText().toString().trim(),getApplicationContext())) {
            return;
        }
        final ProgressDialog progressDialog= ProgressDialog.show(this,"please wait...","Progressing....",true);
        mAuth.createUserWithEmailAndPassword(edtEamil.getText().toString().trim(),edtPass.getText().toString().trim())
                .addOnCompleteListener(DangKi.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            startActivity(new Intent(DangKi.this,EmailAuthActivity.class));
                        }


                    }
                });

    }

}
