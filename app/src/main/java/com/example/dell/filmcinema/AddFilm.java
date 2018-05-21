package com.example.dell.filmcinema;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFilm extends AppCompatActivity {
    private DatabaseReference mDatabase;
    int REQUEST_CODE_GALLERY=100;
    Uri uri;
    String path;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText name,content,end,start,category,actor,time,date,cinema;
    ImageView image,imagedis,imagest,imageed,imagedate;
    EditText link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        //home button
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        addControl();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

    private void addControl() {
        imagedate=findViewById(R.id.imagedate);
        imageed=findViewById(R.id.imageed);
        imagest=findViewById(R.id.imagest);
        link=findViewById(R.id.link);
        name=findViewById(R.id.name);
        content=findViewById(R.id.content);
        start=findViewById(R.id.start);
        category=findViewById(R.id.category);
        actor=findViewById(R.id.actor);
        time=findViewById(R.id.time);
        end=findViewById(R.id.end);
        cinema=findViewById(R.id.cinema);
        date=findViewById(R.id.date);
        imagedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });
        imagest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imagest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(start);
            }
        });
        imageed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(end);
            }
        });
    }

    public void OnClick(View view) {

        String starttime=start.getText().toString();
        String endtime= end.getText().toString();
        String datetime=date.getText().toString();
        String image=link.getText().toString();
        Film film=new Film(name.getText().toString(),content.getText().toString()
                ,image,starttime,endtime,datetime,category.getText().toString(),actor.getText().toString(),cinema.getText().toString());
        mDatabase.child("Film").push().setValue(film);
        name.setText("");
        content.setText("");
        date.setText("");
        start.setText("");
        end.setText("");
        category.setText("");
        actor.setText("");
        cinema.setText("");
        Toast.makeText(AddFilm.this, "Successfull", Toast.LENGTH_SHORT).show();
    }

    public void datePicker() {
        imagedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddFilm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText("" + i2 + "-" + (i1 + 1) + "-" + i);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }


    public void timePicker(final EditText editText){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddFilm.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                editText.setText(i + ":" + i1);

            }
        }, hour, mi, true);
        timePickerDialog.show();

    }


}
