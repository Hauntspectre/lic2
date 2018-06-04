package com.akexorcist.googledirection.sample;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    EditText edttxt_from, edttxt_to, edttxt_to2, edttxt_to3, edttxt_to4;
    String str_from, str_to, str_to2, str_to3, str_to4;
    LocationManager locationManager;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        findViewById(R.id.btn_waypoints).setOnClickListener(this);
        edttxt_from = (EditText) findViewById(R.id.editText_from);
        edttxt_to = (EditText) findViewById(R.id.editText_to);

        edttxt_to2 = (EditText) findViewById(R.id.editText_to2);
        edttxt_to3 = (EditText) findViewById(R.id.editText_to3);
        edttxt_to4 = (EditText) findViewById(R.id.editText_to4);
//        str_from=edttxt_from.getText().toString();
//        str_to=edttxt_to.getText().toString();
//        str_to2=edttxt_to2.getText().toString();
        edttxt_from.setText("Corbeanca,ro");
        edttxt_to.setText("Bucuresti,ro");
        edttxt_to2.setText("Iasi,ro");
        edttxt_to3.setText("Buftea,ro");
        edttxt_to4.setText("Brasov,ro");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_waypoints) {
            // getApplicationContext().getSharedPreferences("current_location_name", 0).edit().clear().commit();


//            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_name", cityName).apply();
//            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_longitude", longitude).apply();
//            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_latitude", latitude).apply();

            str_from = edttxt_from.getText().toString();
            str_to = edttxt_to.getText().toString();
            str_to2 = edttxt_to2.getText().toString();
            str_to3 = edttxt_to3.getText().toString();
            str_to4 = edttxt_to4.getText().toString();
            Intent intent = new Intent(getBaseContext(), WaypointsDirectionActivity.class);
            intent.putExtra("str_from", str_from);
            intent.putExtra("str_to", str_to);
            intent.putExtra("str_to2", str_to2);
            intent.putExtra("str_to3", str_to3);
            intent.putExtra("str_to4", str_to4);
            locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            startActivity(intent);
            //  dialog.dismiss();


        }
    }


    public void goToWaypointsDirection() {
        openActivity(WaypointsDirectionActivity.class);
    }

    public void openActivity(Class<?> cs) {
        startActivity(new Intent(this, cs));
    }


    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        getApplicationContext().getSharedPreferences("current_location_longitude", 0).edit().clear().commit();
        getApplicationContext().getSharedPreferences("current_location_latitude", 0).edit().clear().commit();

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_longitude", String.valueOf(longitude)).apply();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_latitude", String.valueOf(latitude)).apply();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
