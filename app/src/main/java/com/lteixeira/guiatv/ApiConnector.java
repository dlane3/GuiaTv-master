package com.lteixeira.guiatv;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Owner on 4/29/2015.
 */
public class ApiConnector {
    public JSONArray GetFavShows() {

        String result = "";
        InputStream isr = null;

        try {
            HttpURLConnection urlConnection = null;
            URL url = new URL("http://207.191.186.29/~dustin/get_all_favs.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            isr = urlConnection.getInputStream();
        } catch (Exception e) {
            Log.e("LOG_TAG", "PN Error in http connection " + e.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result = sb.toString();
        } catch (Exception e) {
            Log.e("LOG_TAG", "PN Error converting result " + e.toString());
        }

        JSONArray jArray = null;

        // Parse code goes here....
        try {
            jArray = new JSONArray(result);
            //Theoreticaly, the above line of code actually pushes the result into a JSON array called jArray

        } catch (Exception e) {
            Log.e("LOG_TAG", "PN Error parsing data " + e.toString());
        }
        return jArray;
    }


}