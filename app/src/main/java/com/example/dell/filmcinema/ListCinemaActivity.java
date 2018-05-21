package com.example.dell.filmcinema;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListCinemaActivity extends AppCompatActivity {
    ListView lvCinema;
    ArrayList<String> cinemas=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcinema);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvCinema=findViewById(R.id.lvCinema);
        cinemas.add("CGV Hà Đông");
        cinemas.add("CGV Long Biên");
        cinemas.add("Lotte Hà Đông");
        cinemas.add("Lotte Long Biên");
        cinemas.add("Lotte Mart Đống Đa");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cinemas);
        lvCinema.setAdapter(adapter);
        lvCinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ListCinemaActivity.this,GoogleMaps.class);
                intent.putExtra("nameCinema",cinemas.get(position));
                startActivity(intent);
            }
        });

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
