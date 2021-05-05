package com.example.hotelhunter.ui.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrlNearbyPlaces {
    public String ReadUrl(String placeURL) throws IOException{
        String placeData = "";
        InputStream inStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(placeURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            StringBuffer strBuffer = new StringBuffer();

            String line = "";

            while ((line =bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }

            placeData = strBuffer.toString();
            bufferedReader.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inStream.close();
            httpURLConnection.disconnect();
        }
        return placeData;
    }
}
