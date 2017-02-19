package com.malkes.booklisting;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.malkes.booklisting.adapter.BookAdapter;
import com.malkes.booklisting.asynctasks.FetchBooksAsyncTask;
import com.malkes.booklisting.model.Book;
import com.malkes.booklisting.util.Util;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


    }

    private void initViews(){
        mListView = (ListView) findViewById(R.id.listview);
        tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);

        EditText editText = (EditText) findViewById(R.id.et_search);
        if(editText != null){
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        fetchBooks(v.getText().toString());
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    private void fetchBooks(String query){
        if(Util.hasInternetCoonnection(this)){
            new FetchBooksAsyncTask(this){
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Util.hideKeyboard(MainActivity.this);
                    if(s != null){
                        List<Book> bookList = Util.parseBooks(s);
                        if(bookList != null && bookList.size() > 0){
                            mListView.setVisibility(View.VISIBLE);
                            tvErrorMessage.setVisibility(View.GONE);
                            mListView.setAdapter(new BookAdapter(bookList));
                        }else{
                            showMessage();
                        }
                    }else{
                        showMessage();
                    }
                }
            }.execute(query);
        }else{
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_info_outline_black_48dp)
                    .setTitle(R.string.no_internet_title)
                    .setMessage(R.string.no_internet_message)
                    .setPositiveButton(R.string.button_ok,null)
                    .show();
        }

    }

    private void showMessage(){
        tvErrorMessage.setText(R.string.error_message);
        tvErrorMessage.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        mListView.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }

}
