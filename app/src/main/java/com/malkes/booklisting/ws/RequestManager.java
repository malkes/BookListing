package com.malkes.booklisting.ws;

import com.malkes.booklisting.util.Util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Malkes on 16/02/17.
 */

public class RequestManager {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=%s";
    private static final int TIMEOUT_IN_MILLISECONDS   = 1000 * 30; // 30 seconds;

    public static String fetchBooks(String query){
        String response = null;
        try {
            URL url = new URL(String.format(Locale.getDefault(),BASE_URL,query));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                response = Util.inputStreamToString(urlConnection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
