package com.example.x.lekcja9;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int LOCATION_REQUEST_PERMISSION = 101;
    TextView dostawca;
    TextView dlugosc;
    TextView szerokosc;

    Criteria cr = new Criteria();
    Location loc;
    String mojDostawca;

    LocationManager mylm;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dostawca = (TextView) findViewById(R.id.textView1);
        dlugosc = (TextView) findViewById(R.id.textView2);
        szerokosc = (TextView) findViewById(R.id.textView3);
        start = (Button) findViewById(R.id.start);

        int internetLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (internetLocationPermissionCheck != PackageManager.PERMISSION_GRANTED || fineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED || coarseLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_PERMISSION);
        } else {
            start.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("MissingPermission")
                @Override
                public void onClick(View v) {
                    getLokalizacja();
                    getDostawca();
                    getLokalizacjaDostawcy();
                    infoLokalizacja();
                }
            });

            getLokalizacja();
            getDostawca();
            mylm.requestLocationUpdates(mojDostawca, 400, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    getLokalizacjaDostawcy();
                    infoLokalizacja();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    void getLokalizacja() {
        mylm = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    void getDostawca() {
        mojDostawca = mylm.getBestProvider(cr, true);
    }

    @SuppressWarnings("MissingPermission")
    void getLokalizacjaDostawcy() {
        loc = mylm.getLastKnownLocation(mojDostawca);
    }

    void infoLokalizacja() {
        dostawca.setText("dostawca: " + mojDostawca);
        dlugosc.setText("dlugosc geograficzna: " + loc.getLongitude());
        szerokosc.setText("szerokosc geograficzna: " + loc.getLatitude());
    }
}