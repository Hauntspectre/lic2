package com.akexorcist.googledirection.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class WaypointsDirectionActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, DirectionCallback, LocationListener {
    private Button btnRequestDirection;
    private GoogleMap googleMap;
    private String serverKey = "AIzaSyDiyKh9ya2s6T3AqOxQ-wWnipvpGivwlsQ";
    private LatLng start, destionation, destination1, destination2, destination3, destination4;//= new LatLng(41.8838111, -87.6657851);
    //    private LatLng shopping = new LatLng(41.8766061, -87.6556908);
    private LatLng dinner;//= new LatLng(41.8909056, -87.6467561);
    private LatLng gallery;// = new LatLng(41.9007082, -87.6488802);
    Intent intent;
    //double latitude;
    LocationManager locationManager;
    //double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transit_direction);
        getSupportActionBar().hide();
        intent = getIntent();
        btnRequestDirection = findViewById(R.id.btn_request_direction);
        btnRequestDirection.setOnClickListener(this);
        //  String from = intent.getExtras().getString("str_from");
        //    String to1 = intent.getExtras().getString("str_to");


        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        requestDirection();
        //   requestDirectionResume(latitude,longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onClick(View v) {
        //int id = v.getId();
//        if (id == R.id.btn_request_direction) {
//            requestDirection();
//        }
    }

    public void requestDirection() {

        String from = intent.getExtras().getString("str_from");
        String to1 = intent.getExtras().getString("str_to");
        String to2 = intent.getExtras().getString("str_to2");
        String to3 = intent.getExtras().getString("str_to3");
        String to4 = intent.getExtras().getString("str_to4");

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses;
        List<Address> addresses2;
        List<Address> addresses3;
        List<Address> addresses4;
        List<Address> addresses5;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        //   List<Address> currentLocation;


        try {
            addresses = geocoder.getFromLocationName(from, 1);
            addresses2 = geocoder.getFromLocationName(to1, 1);
            addresses3 = geocoder.getFromLocationName(to2, 1);
            addresses4 = geocoder.getFromLocationName(to3, 1);
            addresses5 = geocoder.getFromLocationName(to4, 1);


            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                String s = String.valueOf(latitude) + ";" + String.valueOf(longitude);
                //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                // park = new LatLng(latitude,longitude);


                double latitude2 = addresses2.get(0).getLatitude();
                double longitude2 = addresses2.get(0).getLongitude();
                String s2 = String.valueOf(latitude) + ";" + String.valueOf(longitude);
                // gallery = new LatLng(latitude2,longitude2);

                double latitude3 = addresses3.get(0).getLatitude();
                double longitude3 = addresses3.get(0).getLongitude();
                String s3 = String.valueOf(latitude) + ";" + String.valueOf(longitude);
                // dinner = new LatLng(latitude3,longitude3);

                double latitude4 = addresses4.get(0).getLatitude();
                double longitude4 = addresses4.get(0).getLongitude();

                double latitude5 = addresses5.get(0).getLatitude();
                double longitude5 = addresses5.get(0).getLongitude();


                City fromm = new City(longitude, latitude, from);
                City dest1 = new City(longitude2, latitude2, to1);
                City dest2 = new City(longitude3, latitude3, to2);
                City dest3 = new City(longitude4, latitude4, to3);
                City dest4 = new City(longitude5, latitude5, to4);


                //  String currentLocationName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("current_location_name", "");
                String currentLocationLongitude = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("current_location_longitude", "");
                String currentLocationLatitude = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("current_location_latitude", "");

                City startLocation = new City(Double.parseDouble(currentLocationLongitude), Double.parseDouble(currentLocationLatitude), "current loc");


                //    Toast.makeText(getApplicationContext(),currentLocationLatitude,Toast.LENGTH_LONG).show();
                ArrayList<City> islands = new ArrayList();

                islands.add(startLocation);
                islands.add(fromm);
                islands.add(dest1);
                islands.add(dest2);
                islands.add(dest3);
                islands.add(dest4);

                OptimalDistance opt = new OptimalDistance();
                ArrayList<City> result = opt.computeOptimalTour(islands, 1000.00);


                start = new LatLng(result.get(0).getLatitude(), result.get(0).getLongitude());
                destionation = new LatLng(result.get(1).getLatitude(), result.get(1).getLongitude());
                destination1 = new LatLng(result.get(2).getLatitude(), result.get(2).getLongitude());
                destination2 = new LatLng(result.get(3).getLatitude(), result.get(3).getLongitude());
                destination3 = new LatLng(result.get(4).getLatitude(), result.get(4).getLongitude());
                destination4 = new LatLng(result.get(5).getLatitude(), result.get(5).getLongitude());


                //   moveToCurrentLocation(start);

                Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
                GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
                GoogleDirection.withServerKey(serverKey)
                        .from(start)
                        .and(destionation)
                        .and(destination1)
                        .and(destination2)
                        .and(destination3)
                        .to(destination4)
                        //  .to(park)
                        .transportMode(TransportMode.DRIVING)
                        .execute(this);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void requestDirectionResume(double latCurrent, double longCurrent) {

        String from = intent.getExtras().getString("str_from");
        String to1 = intent.getExtras().getString("str_to");
        String to2 = intent.getExtras().getString("str_to2");
        String to3 = intent.getExtras().getString("str_to3");
        String to4 = intent.getExtras().getString("str_to4");

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses;
        List<Address> addresses2;
        List<Address> addresses3;
        List<Address> addresses4;
        List<Address> addresses5;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        //   List<Address> currentLocation;


        try {
            addresses = geocoder.getFromLocationName(from, 1);
            addresses2 = geocoder.getFromLocationName(to1, 1);
            addresses3 = geocoder.getFromLocationName(to2, 1);
            addresses4 = geocoder.getFromLocationName(to3, 1);
            addresses5 = geocoder.getFromLocationName(to4, 1);


            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                String s = String.valueOf(latitude) + ";" + String.valueOf(longitude);
                //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                // park = new LatLng(latitude,longitude);


                double latitude2 = addresses2.get(0).getLatitude();
                double longitude2 = addresses2.get(0).getLongitude();
                String s2 = String.valueOf(latitude) + ";" + String.valueOf(longitude);
                // gallery = new LatLng(latitude2,longitude2);

                double latitude3 = addresses3.get(0).getLatitude();
                double longitude3 = addresses3.get(0).getLongitude();
                String s3 = String.valueOf(latitude) + ";" + String.valueOf(longitude);
                // dinner = new LatLng(latitude3,longitude3);

                double latitude4 = addresses4.get(0).getLatitude();
                double longitude4 = addresses4.get(0).getLongitude();

                double latitude5 = addresses5.get(0).getLatitude();
                double longitude5 = addresses5.get(0).getLongitude();


                City fromm = new City(longitude, latitude, from);
                City dest1 = new City(longitude2, latitude2, to1);
                City dest2 = new City(longitude3, latitude3, to2);
                City dest3 = new City(longitude4, latitude4, to3);
                City dest4 = new City(longitude5, latitude5, to4);


                //  String currentLocationName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("current_location_name", "");
                String currentLocationLongitude = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("current_location_longitude", "");
                String currentLocationLatitude = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("current_location_latitude", "");

                City startLocation = new City(longCurrent, latCurrent, "current loc");


                //    Toast.makeText(getApplicationContext(),currentLocationLatitude,Toast.LENGTH_LONG).show();
                ArrayList<City> islands = new ArrayList();

                islands.add(startLocation);
                islands.add(fromm);
                islands.add(dest1);
                islands.add(dest2);
                islands.add(dest3);
                islands.add(dest4);

                OptimalDistance opt = new OptimalDistance();
                ArrayList<City> result = opt.computeOptimalTour(islands, 1000.00);


                start = new LatLng(result.get(0).getLatitude(), result.get(0).getLongitude());
                destionation = new LatLng(result.get(1).getLatitude(), result.get(1).getLongitude());
                destination1 = new LatLng(result.get(2).getLatitude(), result.get(2).getLongitude());
                destination2 = new LatLng(result.get(3).getLatitude(), result.get(3).getLongitude());
                destination3 = new LatLng(result.get(4).getLatitude(), result.get(4).getLongitude());
                destination4 = new LatLng(result.get(5).getLatitude(), result.get(5).getLongitude());


                //   moveToCurrentLocation(start);
                Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
                GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
                GoogleDirection.withServerKey(serverKey)
                        .from(start)
                        .and(destionation)
                        .and(destination1)
                        .and(destination2)
                        .and(destination3)
                        .to(destination4)
                        //  .to(park)
                        .transportMode(TransportMode.DRIVING)
                        .execute(this);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDirectionSuccess(final Direction direction, String rawBody) {
        MarkerOptions mpstart = new MarkerOptions();
        mpstart.position(start);
        mpstart.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        googleMap.addMarker(mpstart);
        Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                //   googleMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()));
                MarkerOptions mp = new MarkerOptions();
                mp.position(leg.getEndLocation().getCoordination());

                //  googleMap.addMarker(new MarkerOptions().position(leg.getEndLocation().getCoordination()));
//                if (index == 0) {
////                        MarkerOptions mp = new MarkerOptions();
////
////                        googleMap.addMarker(mp);
//                    // if(index==0){
//                    mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                    //  }
//                }
//                else{
//                    mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
                googleMap.addMarker(mp);
                List<Step> stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getApplicationContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    googleMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);
            btnRequestDirection.setVisibility(View.GONE);
        }

//        MarkerOptions mp = new MarkerOptions();
//        mp.position(start);
//
//        mp.title("my position");
//        mp.icon(();
//        googleMap.addMarker(mp);
//
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                start, 16));
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }

    @Override
    protected void onResume() {
        requestDirection();
        //requestDirection();

//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//
//
        //     requestDirectionResume(latitude, longitude);
//                    }
//                },
//                5000
//        );


        super.onResume();
    }

    @Override
    protected void onPause() {
        googleMap.clear();
        getApplicationContext().getSharedPreferences("current_location_longitude", 0).edit().clear().commit();
        getApplicationContext().getSharedPreferences("current_location_latitude", 0).edit().clear().commit();
        super.onPause();
    }

    @Override
    public void onLocationChanged(final Location location) {
//        (new Thread(new Runnable()
//        {
//
//            @Override
//            public void run()
//            {
//                while (!Thread.interrupted())
//                    try
//                    {
//                        Thread.sleep(11000);
//                        runOnUiThread(new Runnable() // start actions in UI thread
//                        {
//
//                            @Override
//                            public void run()
//                            {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        // requestDirectionResume(location.getLongitude(), location.getLatitude());

        getApplicationContext().getSharedPreferences("current_location_longitude", 0).edit().clear().commit();
        getApplicationContext().getSharedPreferences("current_location_latitude", 0).edit().clear().commit();

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_longitude", String.valueOf(longitude)).apply();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("current_location_latitude", String.valueOf(latitude)).apply();


        //   }
//                        });
//                    }
//                    catch (InterruptedException e)
//                    {
//                        // ooops
//                    }
//            }
//        })).start();


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
