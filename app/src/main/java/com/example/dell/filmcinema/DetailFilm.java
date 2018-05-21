package com.example.dell.filmcinema;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailFilm extends AppCompatActivity {
    ImageView imgdetail;
    TextView name,date,start,end,category,actor;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        id=getIntent().getStringExtra("data");
        addControl();
        if(getIntent().getSerializableExtra("film")!=null){
            Film film= (Film) getIntent().getSerializableExtra("film");
            setFilm(film);
        }


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

    private void setFilm(Film film){
        name.setText(film.getName());
        date.setText("Khởi chiếu"+"          "+film.getDate()+"");
        start.setText("Thời gian bắt đầu"+"   "+film.getStart()+"");
        end.setText("Thời gian kết thúc"+"  "+film.getEnd()+"");
        category.setText("Thế loại"+"            "+film.getCategory());
        actor.setText("Diễn viên"+"          "+film.getActor());
        Glide.with(this)
                .load(film.getImage())
                .into(imgdetail);

    }

    private void addControl() {
        imgdetail = findViewById(R.id.imgdetail);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        category = findViewById(R.id.category);
        actor = findViewById(R.id.actor);
        if(id!=null) {
            FirebaseDatabase.getInstance().getReference().child("Film").child(id)// id=LCMqM35Fg2Wo-GEyC_t
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("datanap",dataSnapshot.getValue().toString());
                    if(dataSnapshot!=null){
                        Film film=dataSnapshot.getValue(Film.class);
                        setFilm(film);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
//


    }
}
