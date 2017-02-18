package com.malkes.booklisting.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.malkes.booklisting.R;
import com.malkes.booklisting.ws.RequestManager;

/**
 * Created by Malkes on 16/02/17.
 */

public class FetchBooksAsyncTask extends AsyncTask<String,Void,String> {

    private ProgressDialog mProgress;

    public FetchBooksAsyncTask(Activity activity){
        mProgress = new ProgressDialog(activity);
        mProgress.setMessage(activity.getResources().getString(R.string.loading_message));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mProgress != null){
            mProgress.show();
        }

    }

    @Override
    protected String doInBackground(String... params) {
        return RequestManager.fetchBooks(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(mProgress != null){
            mProgress.dismiss();
        }
    }
}
