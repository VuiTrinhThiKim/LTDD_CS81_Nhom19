package com.example.hotelhunter.ui.map;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String ggPlaceData, url;
    private GoogleMap ggMap;

    @Override
    protected String doInBackground(Object... objects) {
        ggMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrlNearbyPlaces downloadUrl = new DownloadUrlNearbyPlaces();
        try {
            ggPlaceData = downloadUrl.ReadUrl(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ggPlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parseData(s);

        displayForNearbyPlaces(nearbyPlaceList);
    }

    private void displayForNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for (int index = 0; index <nearbyPlaceList.size(); index++) {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> ggNearbyPlace = nearbyPlaceList.get(index);
            String nameOfPlace = ggNearbyPlace.get("name_of_place");
            String vicinity = ggNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(ggNearbyPlace.get("lat"));
            double lng = Double.parseDouble(ggNearbyPlace.get("lng"));

            LatLng latLngNearbyPlace = new LatLng(lat, lng);
            markerOptions.position(latLngNearbyPlace);
            markerOptions.title(nameOfPlace + ": " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            ggMap.addMarker(markerOptions);
            ggMap.moveCamera(CameraUpdateFactory.newLatLng(latLngNearbyPlace));
            ggMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
}
