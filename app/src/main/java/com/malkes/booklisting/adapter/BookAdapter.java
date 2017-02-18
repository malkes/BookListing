package com.malkes.booklisting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.malkes.booklisting.R;
import com.malkes.booklisting.model.Book;
import com.malkes.booklisting.viewholder.BookViewHolder;

import java.util.List;

/**
 * Created by Malkes on 16/02/17.
 */

public class BookAdapter extends BaseAdapter {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList){
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList == null? 0 : bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookViewHolder viewHolder;
        Context context = parent.getContext();

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item,parent,false);
            viewHolder = new BookViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (BookViewHolder) convertView.getTag();
        }

        Book book = bookList.get(position);
        if(book != null){
            viewHolder.tvTitle.setText(book.getTitle());
            viewHolder.tvAuthor.setText(book.getAuthor());
            viewHolder.tvDescription.setText(book.getDescription());
            Glide.with(context).load(book.getUrlCover()).into(viewHolder.ivCover);
        }

        return convertView;
    }

}
