package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.demoapp.data.StationDummyData;
import com.example.demoapp.models.PoliceStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnShow = findViewById(R.id.btnShow);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.8467, 80.9462), 16));
        loadStationData();

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoliceStation station = (PoliceStation) v.getTag();
                startActivity(new Intent(HomeActivity.this,StationDetailActivity.class));
            }
        });
    }

    private void loadStationData() {
        ArrayList<PoliceStation> load = StationDummyData.load();
        for (PoliceStation policeStation : load) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(policeStation.name)
                    .position(new LatLng(policeStation.lat, policeStation.lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_convenience_store_black_24dp));

            Marker marker = mMap.addMarker(markerOptions);

            marker.setTag(policeStation);
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                PoliceStation station = (PoliceStation) marker.getTag();
                btnShow.setText(station.name+"\n"+station.address);
                btnShow.setTag(station);
                return true;
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
