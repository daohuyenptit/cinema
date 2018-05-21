package com.example.dell.filmcinema;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    RecyclerView recycler;
    ArrayList<Film> films=new ArrayList<>();
    private DatabaseReference mDatabase;
    ArrayList<String> names;
    ListView lv;
    ArrayAdapter arrayAdapter;
    MovieAdapter adapter;
    TextView txtusername;
    Button txtCinema,txtBooking,txtMovies;
    ViewFlipper viewFlipper;
    Animation an_zoomin,an_zoomout;
    LinearLayout linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set textview for header navigation
        View headerview= navigationView.getHeaderView(0);
        txtusername=headerview.findViewById(R.id.txtusername);
        //get user email current
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            txtusername.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }else{
            txtusername.setText("user@gmail.com");
        }
        names =new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        addControls();
    }


    private void addControls() {
        linear=findViewById(R.id.linear);
        viewFlipper=findViewById(R.id.viewFlipper);
        ArrayList<String> viewFlippers=new ArrayList<>();
        viewFlippers.add("http://2sao.vietnamnetjsc.vn/images/2017/07/05/01/06/phim-han-1.jpg");
        viewFlippers.add("http://img.saobiz.net/d/2015/06/700994_01.jpg");
        viewFlippers.add("http://media.hdsieunhanh.com/film/24072017/preview_77311500868151.jpg");
        viewFlippers.add("http://img.hayhaytv.com/film/30012014/no-breathing_82351391057392.jpg");
        viewFlippers.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-Evh0VpQIvOX1eW8LoEZoemu56sKIi6eddYmcVDaM_NAhvoZeKw");
        viewFlippers.add("http://image.bnews.vn/MediaUpload/Medium/2016/06/13/131719-topphim.jpg");
        for (int i = 0; i < viewFlippers.size(); i++) {
            ImageView imageViewFlipper=new ImageView(getApplicationContext());
            Glide.with(this).load(viewFlippers.get(i)).into(imageViewFlipper);
            imageViewFlipper.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageViewFlipper);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_out=AnimationUtils.loadAnimation(this,R.anim.slide_out_right);
        Animation animation_slide_in= AnimationUtils.loadAnimation(this,R.anim.slide_in_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

        txtBooking=findViewById(R.id.txtBooking);
        txtCinema=findViewById(R.id.txtCinema);
        txtMovies=findViewById(R.id.txtMovies);
        txtMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Cinema_FilmActivity.class);
                intent.putExtra("AllFilm","AllFilm");
                startActivity(intent);
            }
        });
        txtBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EmailAuthActivity.class));
            }
        });
        txtCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListCinemaActivity.class);
                startActivity(intent);

            }
        });
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListCinemaActivity.class));

            }
        });

        mDatabase.child("Film").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null){
                    Film film=dataSnapshot.getValue(Film.class);
                    films.add(film);
                    names.add(film.getName());
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
        LinearLayoutManager linearLayout=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(linearLayout);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,linearLayout.getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);
        adapter=new MovieAdapter(films,getApplicationContext());
        recycler.setAdapter(adapter);
    }
    public void animation(){
        an_zoomin=AnimationUtils.loadAnimation(this,R.anim.zoom_in);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_login) {
            startActivity( new Intent(getApplicationContext(), EmailAuthActivity.class));

        } else if (id == R.id.nav_register) {
            startActivity( new Intent(getApplicationContext(), DangKi.class));

        } else if (id == R.id.nav_cinema) {
            startActivity( new Intent(getApplicationContext(), ListCinemaActivity.class));

        } else if (id == R.id.nav_logout) {
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                FirebaseAuth.getInstance().signOut();
                txtusername.setText("user@gmail.com");
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
            int id=v.getId();
            switch (R.id.txtBooking){
                case R.id.txtBooking:
                    break;
                case R.id.txtCinema:
                    Intent intent=new Intent(MainActivity.this,ListCinemaActivity.class);
                    startActivity(intent);
                    break;
                case R.id.txtMovies:
                    break;
                case R.id.linear:


                    break;
            }
        }
}
