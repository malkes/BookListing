package com.malkes.booklisting.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malkes.booklisting.R;

/**
 * Created by Malkes on 16/02/17.
 */

public class BookViewHolder {

    public BookViewHolder(View convertView){
        tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        tvAuthor = (TextView) convertView.findViewById(R.id.tv_author);
        ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
        tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
    }

    public TextView tvTitle;
    public TextView tvAuthor;
    public ImageView ivCover;
    public TextView tvDescription;
}
