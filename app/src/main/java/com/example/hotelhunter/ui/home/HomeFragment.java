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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelhunter.MainActivity;
import com.example.hotelhunter.R;
import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap ggMap;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    EditText edtSearchAutocomplete;
    View mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Check locaton permission
        checkSelfPermission();
        // Assign variable
        edtSearchAutocomplete = view.findViewById(R.id.edit_text_autocomplete);
        // Initialize places
        // Using getString() to get api_key in string.xml
        Places.initialize(getActivity().getApplicationContext(), getString(R.string.api_key));
        // Set non focusable for edtSearchAutocomplete
        edtSearchAutocomplete.setFocusable(false);
        edtSearchAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize fieldList of place
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                // Create autocomplete intent
                Intent intentAutocomplete = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                                                            .build(getActivity());
                // Start activity result
                startActivityForResult(intentAutocomplete, PERMISSIONS_REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Initialize place when success
            Place placeAutocomplete = Autocomplete.getPlaceFromIntent(data);
            // Set address on EditText
            edtSearchAutocomplete.setText(placeAutocomplete.getAddress());
            // Set icon
            edtSearchAutocomplete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_location_on,0,0,0);
            // Create marker and move camera to location searched
            LatLng latLngSearch = placeAutocomplete.getLatLng();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLngSearch);
            markerOptions.title(placeAutocomplete.getName());
            //Clear map
            ggMap.clear();
            // Initialize camere update
            CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(latLngSearch,18);
            // Set animate camera for camUpdate and add Marker
            ggMap.animateCamera(camUpdate);
            ggMap.addMarker(markerOptions);

        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Initalize status when error
            Status status = Autocomplete.getStatusFromIntent(data);
            //Display toast
            Toast.makeText(getActivity().getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Set ggMap = googlleMap
        ggMap = googleMap;
        //Enabled my location
        ggMap.setMyLocationEnabled(true);
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                                        .getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = ((RelativeLayout.LayoutParams) locationButton.getLayoutParams());

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,30,165);
        }
        ggMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                // Create laititude and longtitude of the user location
                LatLng latLngHome = new LatLng(location.getLatitude(), location.getLongitude());
                // Initialize marker options
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLngHome);
                markerOptions.title("Home");
                // Initialize camere update
                CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(latLngHome,18);
                // Set animate camera for camUpdate and add Marker
                ggMap.animateCamera(camUpdate);
                ggMap.addMarker(markerOptions);

            }
        });
    }

    private void checkSelfPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // The user did not grant permission
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE );
        }
        else
        {
            // The user granted permission
            SupportMapFragment spMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapView = spMapFragment.getView();
            spMapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The user granted permission
                    SupportMapFragment spMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapView = spMapFragment.getView();
                    spMapFragment.getMapAsync(this);
                } else {
                    //permission denied, bool Dissable
                }
                return;
            }
        }
    }
}