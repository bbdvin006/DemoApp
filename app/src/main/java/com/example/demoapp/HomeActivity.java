package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoapp.data.StationDummyData;
import com.example.demoapp.models.PoliceStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ConstraintLayout infosheet;
    private boolean isUp;
    private TextView textAddr;
    private TextView textStation;
    private TextView textArea;
    private TextView textLoc;
    private Button btnView;
    private TextView textContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        infosheet = findViewById(R.id.info_card);
        FloatingActionButton fab = findViewById(R.id.fab);
        textAddr = findViewById(R.id.textAddr);
        textStation = findViewById(R.id.textStation);
        textArea = findViewById(R.id.textArea);
        textLoc = findViewById(R.id.textloc);
        textContact = findViewById(R.id.textContact);
        btnView = findViewById(R.id.btnView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.913855, 80.937594), 12));
        loadStationData();
        isUp = false;
        /*infosheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoStation(v);
            }
        });*/
    }

    private void gotoStation(View v) {
        PoliceStation station = (PoliceStation) v.getTag();
        Intent intent = new Intent(HomeActivity.this, DetailStationActivity.class);
        intent.putExtra("station_name", station.name);
        intent.putExtra("station_address", station.address);
        intent.putExtra("station_contact", station.mobile);
        startActivity(intent);
    }

    private void loadStationData() {
        ArrayList<PoliceStation> load = StationDummyData.load();
        for (PoliceStation policeStation : load) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(policeStation.name)
                    .position(new LatLng(policeStation.lat, policeStation.lng));
            Marker marker = mMap.addMarker(markerOptions);
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.station));
            marker.setTag(policeStation);
            marker.setTitle(policeStation.name);
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
                PoliceStation station = (PoliceStation) marker.getTag();

                slideUp(station);
                return true;
            }
        });
    }

    private void slideUp(PoliceStation station) {

        textAddr.setText(station.address);
        textStation.setText(station.name);
        textArea.setText(station.area);
        textLoc.setText("geo:" + station.lat + "," + station.lng);
        textContact.setText(station.mobile);
        btnView.setTag(station);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoStation(v);
            }
        });

        infosheet.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, infosheet.getHeight(), 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        infosheet.startAnimation(animate);
    }

    private void slideDown() {
        infosheet.setVisibility(View.INVISIBLE);
        isUp = false;
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, infosheet.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        infosheet.startAnimation(animate);
    }


}
