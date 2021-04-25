package com.example.hotelhunter.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelhunter.MainActivity;
import com.example.hotelhunter.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {

    private GoogleMap ggMap;
    double latHome, lngHome;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            ggMap = googleMap;

            ggMap.setMyLocationEnabled(true);
            ggMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    LatLng latLngHome = new LatLng(latHome,lngHome);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLngHome);
                    markerOptions.title("Vị tri hiện tại");
                    ggMap.clear();
                    CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(latLngHome, 15);
                    ggMap.animateCamera(camUpdate);
                    ggMap.addMarker(markerOptions);

                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        checksSelfPermission();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getCurrentLocationNow();
                    SupportMapFragment spMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    spMapFragment.getMapAsync(callback);
                }
                else {
                    Toast.makeText(getContext(), "Permission denied!!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocationNow() {
        LocationManager locaManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locaManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locaManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location locationRe = task.getResult();

                    if (locationRe != null) {
                        latHome = locationRe.getLatitude();
                        lngHome = locationRe.getLongitude();
                    }
                    else {
                        LocationRequest locaRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locaCallBack = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location locationCB = locationResult.getLastLocation();

                                latHome = locationCB.getLatitude();
                                lngHome = locationCB.getLongitude();
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locaRequest,locaCallBack, Looper.myLooper());
                    }
                }
            });
        }
        else {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void checksSelfPermission(){
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocationNow();
            SupportMapFragment spMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            spMapFragment.getMapAsync(callback);
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_CODE);
        }
    }
    
}