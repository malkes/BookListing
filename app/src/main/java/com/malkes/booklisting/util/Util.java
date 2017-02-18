package com.malkes.booklisting.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.malkes.booklisting.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malkes on 16/02/17.
 */

public class Util {

    private static final String BOOK_ITEMS_KEY = "items";
    private static final String BOOK_VOLUME_INFO_KEY = "volumeInfo";
    private static final String BOOK_AUTHORS_KEY = "authors";
    private static final String BOOK_TITLE_KEY = "title";
    private static final String BOOK_IMAGE_LINKS_KEY = "imageLinks";
    private static final String BOOK_THUMB_KEY = "thumbnail";
    private static final String BOOK_DESCRIPTION_KEY = "description";

    public static String inputStreamToString(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line ;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public static List<Book> parseBooks(String jsonString){
        List<Book> bookList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray jsonArray = json.getJSONArray(BOOK_ITEMS_KEY);

            for (int i = 0; i < jsonArray.length(); i++) {
                bookList.add(extractBook(jsonArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    private static Book extractBook(JSONObject json){
        Book book = new Book();

        try {
            JSONObject volumeInfo = json.getJSONObject(BOOK_VOLUME_INFO_KEY);
            book.setTitle( volumeInfo.getString(BOOK_TITLE_KEY) );
            book.setAuthor( volumeInfo.getJSONArray(BOOK_AUTHORS_KEY).getString(0) );
            book.setUrlCover( volumeInfo.getJSONObject(BOOK_IMAGE_LINKS_KEY).getString(BOOK_THUMB_KEY) );
            book.setDescription( volumeInfo.getString(BOOK_DESCRIPTION_KEY) );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return book;
    }

    public static boolean hasInternetCoonnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
