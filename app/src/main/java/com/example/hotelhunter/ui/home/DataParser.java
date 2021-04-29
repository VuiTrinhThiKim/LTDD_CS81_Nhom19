package com.example.hotelhunter.ui.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getSingleNearbyPlace(JSONObject ggPlaceJSON) {
        HashMap<String, String> strPlaceMap = new HashMap<>();

        String namePlace = "-NA-";
        String vicinityPlace = "-NA-";
        String latPlace = "";
        String lngPlace = "";
        String reference = "";

        try {
            if (ggPlaceJSON.isNull("name")) {
                namePlace = ggPlaceJSON.getString("name");
            }
            if (ggPlaceJSON.isNull("vicinity")) {
                vicinityPlace = ggPlaceJSON.getString("vicinity");
            }
            latPlace = ggPlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            lngPlace = ggPlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = ggPlaceJSON.getString("reference");

            strPlaceMap.put("name_of_place", namePlace);
            strPlaceMap.put("vicinity", vicinityPlace);
            strPlaceMap.put("lat", latPlace);
            strPlaceMap.put("lng", lngPlace);
            strPlaceMap.put("reference", reference);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return strPlaceMap;
    }

    private List<HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray) {
        int count = jsonArray.length();

        List<HashMap<String, String>> nearbyPlaceList = new ArrayList<>();

        HashMap<String, String> nearbyPlaceMap = null;

        for (int index = 0; index < count; index++) {
            try {
                nearbyPlaceMap = getSingleNearbyPlace( (JSONObject) jsonArray.get(index));
                nearbyPlaceList.add(nearbyPlaceMap);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearbyPlaceList;
    }

    public List<HashMap<String, String>> parseData (String JSONData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSONData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  getAllNearbyPlaces(jsonArray);
    }

}
