package com.example.dell.filmcinema;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cinema_FilmActivity extends AppCompatActivity {
    ArrayList<Film> films=new ArrayList<>();
    Cinema_MovieAdapter adapter;
    RecyclerView recycler;
    private DatabaseReference mDatabase;
    String cinema=null;
    String allFilm=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema__film);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        cinema= i.getStringExtra("cinema");
        allFilm=i.getStringExtra("AllFilm");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Film").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("DataSnapshots", dataSnapshot.getKey() + "- " + dataSnapshot.getValue());
                Film film=dataSnapshot.getValue(Film.class);
                if(cinema!=null){
                    if(film.getCinema().equals(cinema)){
                        films.add(film);
                        adapter.notifyDataSetChanged();
                    }

                }else if(allFilm.equalsIgnoreCase("AllFilm")){
                    films.add(film);
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recycler=findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(linearLayout);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,linearLayout.getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);
        adapter=new Cinema_MovieAdapter(films,getApplicationContext());
        recycler.setAdapter(adapter);

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
}
