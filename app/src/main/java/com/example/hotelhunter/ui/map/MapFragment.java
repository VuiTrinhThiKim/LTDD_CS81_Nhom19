package com.example.hotelhunter.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.hotelhunter.MainActivity;
import com.example.hotelhunter.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static android.widget.Toast.makeText;
import static com.example.hotelhunter.ui.map.GetDirectionsAsyncTask.DESTINATION_LAT;
import static com.example.hotelhunter.ui.map.GetDirectionsAsyncTask.DESTINATION_LONG;
import static com.example.hotelhunter.ui.map.GetDirectionsAsyncTask.DIRECTIONS_MODE;
import static com.example.hotelhunter.ui.map.GetDirectionsAsyncTask.USER_CURRENT_LAT;
import static com.example.hotelhunter.ui.map.GetDirectionsAsyncTask.USER_CURRENT_LONG;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap ggMap;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private Polyline newPolyline;
    private LatLngBounds latlngBounds;
    private static LatLng AMSTERDAM;
    private static LatLng PARIS;
    EditText edtSearchAutocomplete;
    View mapView;
    Button btn_direct;

    public MapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
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

                Intent intentAutocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                                                            .build(getActivity());

                // Start activity result
                startActivityForResult(intentAutocomplete, PERMISSIONS_REQUEST_CODE);

            }
        });

    return view;
    }

    /*btn_direct = btn_direct.findViewById(R.id.btn_direction);
        Places.initialize(getActivity().getApplicationContext(), getString(R.string.api_key));

        btn_direct.setFocusable(false);
        btn_direct.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MapFragment.this.getContext(),"Bạn đã click",
                    Toast.LENGTH_LONG).show();
            findDirections(21.0169598, 105.8114176,20.9889196,105.8635858,null );
        }
    }); */

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
            makeText(getActivity().getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
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
        ggMap.getUiSettings().setZoomControlsEnabled(true);
        ggMap.getUiSettings().setCompassEnabled(true);
        ggMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                                        .getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = ((RelativeLayout.LayoutParams) locationButton.getLayoutParams());

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,30,1500);
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
        AMSTERDAM = new LatLng(21.0169598, 105.8114176);
        ggMap.addMarker(new MarkerOptions().position(AMSTERDAM).title("Marker in Sydney"));
        ggMap.moveCamera(CameraUpdateFactory.newLatLng(AMSTERDAM));

        PARIS = new LatLng(20.9889196,105.8635858);
    }

    void checkSelfPermission(){
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

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(
                Color.RED);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null) {
            newPolyline.remove();
        }
        newPolyline = ggMap.addPolyline(rectLine);
        latlngBounds = createLatLngBoundsObject(AMSTERDAM,PARIS);
        ggMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                latlngBounds, 400, 400, 150));

    }
    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation,
                                                  LatLng secondLocation) {
        if (firstLocation != null && secondLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat,
                               double fromPositionDoubleLong, double toPositionDoubleLat,
                               double toPositionDoubleLong, String mode) {
        //button.findViewById(R.id.btn_direction);
        Map<String, String> map = new HashMap<>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT,
                String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG,
                String.valueOf(fromPositionDoubleLong));
        map.put(DESTINATION_LAT,
                String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG,
                String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
        //button.setOnClickListener((View.OnClickListener) asyncTask);
         //asyncTask.cancel(true);
    }


    }