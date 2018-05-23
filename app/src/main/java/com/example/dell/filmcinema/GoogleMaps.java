package com.example.dell.filmcinema;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Button edtGiaVe, edtGoi, txtSuatChieu;
    String nameCinema;
    ViewFlipper viewFlipper;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
        addControl();
        Intent intent = getIntent();
        nameCinema = intent.getStringExtra("nameCinema");
    }

    private void addControl() {
        txtSuatChieu = findViewById(R.id.txtSuatChieu);
        edtGoi = findViewById(R.id.txtGoi);
        viewFlipper = findViewById(R.id.viewFlipper);
        ArrayList<String> imagecinema = new ArrayList<>();
        imagecinema.add("http://thegioidienanh.vn/stores/news_dataimages/hath/122017/15/11/2828_1_4.jpg");
        imagecinema.add("http://www.marcustheatres.com/media/images/gallery-images/ridge-cinema-new-berlin/47-ridge-exteriorjpg.jpg");
        imagecinema.add("http://www.marcustheatres.com/media/images/gallery-images/ridge-cinema-new-berlin/47-ridge-exteriorjpg.jpg");
        for (int i = 0; i < imagecinema.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(this).load(imagecinema.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        Animation animation_slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (nameCinema.equalsIgnoreCase("CGV Hà Đông")) {
            latLng = new LatLng(20.978992, 105.786594);

        } else if (nameCinema.equalsIgnoreCase("CGV Long Biên")) {
            latLng = new LatLng(21.027006, 105.900616);
        } else if (nameCinema.equalsIgnoreCase("Lotte Hà Đông")) {
            latLng = new LatLng(20.963873, 105.771769);
        } else if (nameCinema.equalsIgnoreCase("Lotte Long Biên")) {
            latLng = new LatLng(21.051151, 105.893771);
        } else if (nameCinema.equalsIgnoreCase("Lotte Mart Đống Đa")) {
            latLng = new LatLng(21.005657, 105.823697);
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(nameCinema));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txtSuatChieu:
                Intent i = new Intent(this, Cinema_FilmActivity.class);
                i.putExtra("cinema", nameCinema);
                startActivity(i);

                break;
            case R.id.txtGoi:
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:123456789"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(call);
                break;
        }

    }
}
