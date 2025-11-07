package com.example.libraryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {

    Context context;
    ArrayList<Book> list;

    public BookAdapter(Context context, ArrayList<Book> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_book, parent, false);
        }

        Book b = list.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvSub = convertView.findViewById(R.id.tvSub);

        tvTitle.setText(b.title);
        tvSub.setText(b.author + " • " + b.genre + " • " + b.year);

        return convertView;
    }
}
